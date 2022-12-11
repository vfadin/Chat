package com.vfadin.events.data.repo

import com.vfadin.events.data.datasource.HttpChatRemoteDataSource
import com.vfadin.events.data.entity.forgotPassword.ApiResetPassword
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.login.Captcha
import com.vfadin.events.domain.repo.IForgotPasswordRepo

class ForgotPasswordRepo(
    private val dataSource: HttpChatRemoteDataSource
) : IForgotPasswordRepo {
    override suspend fun sendResetPasswordEmail(email: String): String {
        return when (val response =
            dataSource.sendResetPasswordEmail(email)) {
            is RequestResult.Success -> ""
            is RequestResult.Error -> {
                return response.data ?: "Неизвестная ошибка"
            }
        }
    }

    override suspend fun resetPassword(
        token: String,
        newPassword: String,
        captcha: Captcha,
        userId: Int
    ): RequestResult<String> {
        return when (val response = dataSource.resetPassword(
            ApiResetPassword(
                password = newPassword,
                token = token
            )
        )) {
            is RequestResult.Success -> RequestResult.Success("")
            is RequestResult.Error -> {
                return RequestResult.Error(
                    data = "Неизвестная ошибка",
                    exception = response.exception
                )
            }
        }
    }
}