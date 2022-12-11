package com.vfadin.events.data.entity.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRegistrationResponse(
    @Json(name = "message")
    val message: String?,
)
