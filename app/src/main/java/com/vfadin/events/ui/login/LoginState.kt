package com.vfadin.events.ui.login

data class LoginState(
    val login: String = "",
    val password: String = "",
    val isLoginSuccess: Boolean = false,
    val isLoginLoading: Boolean = false,
    val isLoginError: Boolean = false
)