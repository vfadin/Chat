package com.vfadin.events.ui.login.forgotPassword.newPassword

import com.vfadin.events.domain.entity.login.Captcha

data class NewPasswordState(
    val password: String = "",
    val passwordError: String = "",
    val passwordConfirmation: String = "",
    val passwordConfirmationError: String = "",
    val captcha : Captcha = Captcha("", "", ""),
    val captchaError: String = "",
    val isSuccess: Boolean = false,
)