package com.vfadin.events.domain.repo

import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.login.Captcha

interface IForgotPasswordRepo {
    suspend fun sendResetPasswordEmail(email: String): String
    suspend fun resetPassword(
        token: String,
        newPassword: String,
        captcha: Captcha,
        userId: Int
    ): RequestResult<String>
}