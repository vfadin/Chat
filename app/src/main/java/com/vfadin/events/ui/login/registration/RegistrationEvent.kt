package com.vfadin.events.ui.login.registration

sealed class RegistrationEvent {
    data class LoginChanged(val login: String) : RegistrationEvent()
    data class EmailChanged(val email: String) : RegistrationEvent()
    data class PasswordChanged(val password: String) : RegistrationEvent()
    data class PasswordConfirmationChanged(val passwordConfirmation: String) : RegistrationEvent()
    object RegistrationClicked : RegistrationEvent()
}
