package com.vfadin.events.data.entity.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiStartChatBody(
    @Json(name = "users")
    val users: List<Int>?,
)