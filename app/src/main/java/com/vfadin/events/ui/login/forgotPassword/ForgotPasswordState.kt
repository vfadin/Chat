package com.vfadin.events.ui.login.forgotPassword

import com.vfadin.events.domain.entity.login.Captcha

data class ForgotPasswordState(
    val email: String = "",
    val emailError: String = "",
    val isLoading : Boolean = false,
    val isSendSuccess : Boolean = false
)