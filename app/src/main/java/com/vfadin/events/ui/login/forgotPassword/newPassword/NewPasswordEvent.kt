package com.vfadin.events.ui.login.forgotPassword.newPassword

sealed class NewPasswordEvent{
        data class NewPasswordChanged(val password: String) : NewPasswordEvent()
        data class ConfirmPasswordChanged(val password: String) : NewPasswordEvent()
        data class OnCaptchaTextChanged(val text: String) : NewPasswordEvent()
        object SubmitClicked : NewPasswordEvent()
}
