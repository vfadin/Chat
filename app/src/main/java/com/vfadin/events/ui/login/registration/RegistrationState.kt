package com.vfadin.events.ui.login.registration

data class RegistrationState(
    val email: String = "",
    val emailError: String = "",
    val login: String = "",
    val loginError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val passwordConfirmation: String = "",
    val passwordConfirmationError: String = "",
    val isRegistrationSuccess: Boolean = false,
    val isRegistrationLoading: Boolean = false,
)