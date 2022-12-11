package com.vfadin.events.ui.login.forgotPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.login.Captcha
import com.vfadin.events.domain.repo.IForgotPasswordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordRepo: IForgotPasswordRepo
) : ViewModel() {

    var state by mutableStateOf(ForgotPasswordState())
        private set

    private val _captchaStateFlow = MutableStateFlow<Captcha?>(null)
    val captchaStateFlow = _captchaStateFlow.asStateFlow().filterNotNull()

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChanged -> {
                state = state.copy(email = event.email, emailError = "")
            }
            ForgotPasswordEvent.SendClicked -> {
                sendClicked()
            }
        }
    }

    private fun sendClicked() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val response = forgotPasswordRepo.sendResetPasswordEmail(
                state.email
            )
            state = state.copy(isLoading = false)
            state = if (response.isEmpty()) {
                state.copy(isSendSuccess = true)
            } else {
                state.copy(emailError = response)
            }
        }
    }
}
