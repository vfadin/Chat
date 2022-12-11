package com.vfadin.events.data.entity.forgotPassword

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResetPasswordSendEmail(
    @Json(name = "email")
    val email: String,
)
