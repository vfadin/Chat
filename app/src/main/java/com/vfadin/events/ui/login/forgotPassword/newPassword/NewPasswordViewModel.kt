package com.vfadin.events.ui.login.forgotPassword.newPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.login.Captcha
import com.vfadin.events.domain.repo.IForgotPasswordRepo
import com.vfadin.events.domain.usecase.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPasswordViewModel @Inject constructor(
    private val forgotPasswordRepo: IForgotPasswordRepo
) : ViewModel() {

    var state = NewPasswordState()
    var token = ""
    private var chosenUserId = -1
    var isError by mutableStateOf(false)
        private set

    fun onEvent(event: NewPasswordEvent) {
        when (event) {
            is NewPasswordEvent.ConfirmPasswordChanged -> {
                state = state.copy(passwordConfirmation = event.password)
            }
            is NewPasswordEvent.NewPasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is NewPasswordEvent.OnCaptchaTextChanged -> {
                state = state.copy(
                    captcha = Captcha(
                        state.captcha.key,
                        state.captcha.image,
                        event.text
                    )
                )
            }
            NewPasswordEvent.SubmitClicked -> {
                if (submit()) {
                    resetPassword()
                }
            }
        }
    }

    private fun submit(): Boolean {
        val validatePassword = ValidatePassword()
        val validationPasswordResult = validatePassword.execute(state.password)
        val validationConfirmPasswordResult = validatePassword.execute(state.passwordConfirmation)
        val isSuccessful = listOf(
            validationPasswordResult,
            validationConfirmPasswordResult
        ).all { it.successful }
        if (!isSuccessful) {
            state = state.copy(
                passwordError = validationPasswordResult.errorMessage,
                passwordConfirmationError = validationConfirmPasswordResult.errorMessage
            )
            return false
        }
        return true
    }

    private fun resetPassword() {
        viewModelScope.launch {
            when (val response = forgotPasswordRepo.resetPassword(
                token,
                state.password,
                state.captcha,
                chosenUserId
            )) {
                is RequestResult.Success -> {
                    state = state.copy(isSuccess = true)
                }
                is RequestResult.Error -> {
                    state = state.copy(
                        captchaError = response.data ?: "Ошибка сервера",
                    )
                    isError = true
                }
            }
        }
    }
}