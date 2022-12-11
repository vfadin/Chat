package com.vfadin.events.data.entity.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiChangeUsername(
    @Json(name = "user_name")
    val username: String
)
