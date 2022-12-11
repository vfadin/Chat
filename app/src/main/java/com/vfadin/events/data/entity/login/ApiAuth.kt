package com.vfadin.events.data.entity.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiAuth(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
)