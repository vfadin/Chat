package com.vfadin.events.data.repo

import com.vfadin.events.data.datasource.HttpChatRemoteDataSource
import com.vfadin.events.data.entity.login.ApiRegistration
import com.vfadin.events.data.entity.login.ApiRegistrationResponse
import com.vfadin.events.data.entity.login.toLoginResponse
import com.vfadin.events.data.network.convert
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.login.LoginResponse
import com.vfadin.events.domain.entity.login.RegistrationResponse
import com.vfadin.events.domain.entity.login.toRegistrationResponse
import com.vfadin.events.domain.repo.ILoginRepo
import com.vfadin.events.util.SharedPrefs
import java.util.*

class LoginRepo(
    private val dataSource: HttpChatRemoteDataSource,
    private val sharedPreferences: SharedPrefs
) : ILoginRepo {

    override suspend fun login(
        login: String,
        password: String
    ): RequestResult<LoginResponse> {
        return when (val response =
            dataSource.auth(login, password)) {
            is RequestResult.Success -> {
                with(response.result) {
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.add(Calendar.SECOND, 36000)
                    sharedPreferences.saveToken(
                        data?.token ?: "",
                        "Bearer",
                        calendar.timeInMillis
                    )
                    RequestResult.Success(toLoginResponse())
                }
            }
            is RequestResult.Error -> {
                RequestResult.Error(
                    response.exception
                )
            }
        }
    }

    override suspend fun registration(
        login: String,
        password: String,
        passwordConfirmation: String,
        email: String,
    ): RequestResult<RegistrationResponse> {
        return when (val response = dataSource.registration(
            ApiRegistration(
                username = login,
                password = password,
                email = email,
            )
        )) {
            is RequestResult.Success -> {
                RequestResult.Success(
                    response.result.toRegistrationResponse()
                )
            }
            is RequestResult.Error -> {
                var responseErrorMessage = ""
                response.data?.let { json ->
                    val res = convert<ApiRegistrationResponse>(json)
                    res?.let {
                        responseErrorMessage = it.message ?: ""
                    }
                }
                RequestResult.Error(response.exception, responseErrorMessage)
            }
        }
    }
}