package com.vfadin.events.ui.login

sealed class LoginEvent {
    data class LoginChanged(val login: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object LoginClicked : LoginEvent()
}
