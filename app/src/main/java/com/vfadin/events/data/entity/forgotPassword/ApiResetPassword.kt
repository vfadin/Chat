package com.vfadin.events.data.entity.forgotPassword

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResetPassword(
    @Json(name = "token")
    val token: String,
    @Json(name = "password")
    val password: String
)
