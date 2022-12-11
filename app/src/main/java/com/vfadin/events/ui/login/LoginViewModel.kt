package com.vfadin.events.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.repo.ILoginRepo
import com.vfadin.events.domain.usecase.ValidateEmail
import com.vfadin.events.domain.usecase.ValidateLogin
import com.vfadin.events.domain.usecase.ValidatePassword
import com.vfadin.events.ui.login.registration.RegistrationEvent
import com.vfadin.events.ui.login.registration.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: ILoginRepo
) : ViewModel() {
    var registrationState by mutableStateOf(RegistrationState())
        private set
    var loginState by mutableStateOf(LoginState())
        private set

    val selectedTabIndexState = mutableStateOf(0)
    val dialogOpened = mutableStateOf(false)

    init {
        loginState = loginState.copy(
            login = "sad_456@ngs.ru",
            password = "Admin00!"
        )
        registrationState = registrationState.copy(
            email = "1@q.ru",
            login = "zxcvbnm",
            password = "Admin00!",
            passwordConfirmation = "Admin00!"
        )
    }

    fun onLoginEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginChanged -> {
                loginState = loginState.copy(
                    login = event.login
                )
            }
            is LoginEvent.PasswordChanged -> {
                loginState = loginState.copy(
                    password = event.password
                )
            }
            LoginEvent.LoginClicked -> {
                loginState = loginState.copy(isLoginLoading = true)
                viewModelScope.launch(Dispatchers.IO) {
                    when (val response = loginRepo.login(
                        loginState.login,
                        loginState.password,
                    )) {
                        is RequestResult.Success -> {
                            loginState = loginState.copy(
                                login = loginState.login,
                                password = loginState.password,
                                isLoginSuccess = true,
                                isLoginLoading = false
                            )
                        }
                        is RequestResult.Error -> {
                            loginState =
                                loginState.copy(isLoginLoading = false, isLoginError = true)
                        }
                    }
                }
            }
        }
    }

    fun onRegistrationEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EmailChanged -> {
                registrationState = registrationState.copy(email = event.email)
            }
            is RegistrationEvent.LoginChanged -> {
                registrationState = registrationState.copy(login = event.login)
            }
            is RegistrationEvent.PasswordChanged -> {
                registrationState = registrationState.copy(password = event.password)
            }
            is RegistrationEvent.PasswordConfirmationChanged -> {
                registrationState =
                    registrationState.copy(passwordConfirmation = event.passwordConfirmation)
            }
            RegistrationEvent.RegistrationClicked -> {
                if (submitRegistration()) {
                    registration()
                }
            }
        }
    }

    private fun submitRegistration(): Boolean {
        val validateEmail = ValidateEmail().execute(registrationState.email)
        val validateLogin = ValidateLogin().execute(registrationState.login)
        val validatePassword = ValidatePassword().execute(registrationState.password)
        val validatePasswordConfirmation = ValidatePassword().match(
            registrationState.password,
            registrationState.passwordConfirmation
        )
        val successfulValidation = listOf(
            validateEmail,
            validateLogin,
            validatePassword,
            validatePasswordConfirmation,
        ).all { it.successful }
        if (!successfulValidation) {
            registrationState = registrationState.copy(
                emailError = validateEmail.errorMessage,
                loginError = validateLogin.errorMessage,
                passwordError = validatePassword.errorMessage,
                passwordConfirmationError = validatePasswordConfirmation.errorMessage,
            )
            return false
        }
        return true
    }

    private fun registration() {
        viewModelScope.launch(Dispatchers.IO) {
            registrationState = registrationState.copy(isRegistrationLoading = true)
            when (val response = loginRepo.registration(
                registrationState.login,
                registrationState.password,
                registrationState.passwordConfirmation,
                registrationState.email,
            )) {
                is RequestResult.Success -> {
                    registrationState = registrationState.copy(
                        isRegistrationSuccess = true,
                        isRegistrationLoading = false,
                    )
                }
                is RequestResult.Error -> {
                    registrationState = registrationState.copy(
                        isRegistrationLoading = false,
                        emailError = if (response.data?.contains(
                                "email",
                                true
                            ) == true
                        ) response.data else "",
                        passwordError = if (response.data?.contains(
                                "пароль",
                                true
                            ) == true
                        ) response.data else "",
                    )
                }
            }
        }
    }

    fun onRegistrationSuccess() {
        dialogOpened.value = true
        selectedTabIndexState.value = 0
    }

    fun toastShown() {
        loginState = loginState.copy(isLoginError = false)
    }
}