package com.vfadin.events.ui.login.forgotPassword

sealed class ForgotPasswordEvent {
    data class EmailChanged(val email: String) : ForgotPasswordEvent()
    object SendClicked : ForgotPasswordEvent()
}