package com.vfadin.events.data.entity.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vfadin.events.domain.entity.login.LoginResponse

@JsonClass(generateAdapter = true)
data class ApiAuthResponse(
    @Json(name = "message")
    val message: String?,
    @Json(name = "data")
    val data: ApiAuthResponseData?,
)

@JsonClass(generateAdapter = true)
data class ApiAuthResponseData(
    @Json(name = "token")
    val token: String?,
)

fun ApiAuthResponse.toLoginResponse() = LoginResponse(
    message = message ?: "",
    token = data?.token ?: "",
    tokenType = "Bearer"
)