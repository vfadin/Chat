package com.vfadin.events.data.datasource

import com.vfadin.events.data.entity.login.ApiAuth
import com.vfadin.events.data.entity.login.ApiRegistration
import com.vfadin.events.data.entity.forgotPassword.ApiResetPassword
import com.vfadin.events.data.entity.forgotPassword.ApiResetPasswordSendEmail
import com.vfadin.events.data.entity.home.ApiStartChatBody
import com.vfadin.events.data.entity.profile.ApiChangeName
import com.vfadin.events.data.entity.profile.ApiChangeSurname
import com.vfadin.events.data.entity.profile.ApiChangeUsername
import com.vfadin.events.data.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpChatRemoteDataSource @Inject constructor(
    private val api: IHttpChatService
) {

    suspend fun resetPassword(apiResetPassword: ApiResetPassword) = safeApiCall {
        api.resetPassword(apiResetPassword)
    }

    suspend fun sendResetPasswordEmail(email: String) = safeApiCall {
        api.sendResetPasswordEmail(ApiResetPasswordSendEmail(email))
    }

    suspend fun registration(apiRegistration: ApiRegistration) = safeApiCall {
        api.registration(apiRegistration)
    }

    suspend fun auth(username: String, password: String) = safeApiCall {
        api.auth(ApiAuth(username, password))
    }

    suspend fun getProfile(token: String) = safeApiCall {
        api.getProfile(token)
    }

    suspend fun getMessages(token: String, chatId: Int) = safeApiCall {
        api.getMessages(token, chatId)
    }

    suspend fun startChat(token: String, startChat: ApiStartChatBody) = safeApiCall {
        api.startChat(token, startChat)
    }

    suspend fun getRooms(token: String) = safeApiCall {
        api.getRooms(token)
    }

    suspend fun changeNickname(token: String, nickname: String) = safeApiCall {
        api.changeUsername(token, ApiChangeUsername(nickname))
    }

    suspend fun changeName(token: String, name: String) = safeApiCall {
        api.changeName(token, ApiChangeName(name.substringBefore(" ")))
        api.changeSurname(token, ApiChangeSurname(name.substringAfter(" ")))
    }

    suspend fun getUsers(token: String) = safeApiCall {
        api.getUsers(token)
    }
}