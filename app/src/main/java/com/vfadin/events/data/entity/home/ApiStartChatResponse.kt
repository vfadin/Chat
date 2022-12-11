package com.vfadin.events.data.entity.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiStartChatResponse(
    @Json(name = "data")
    val data: ApiStartChatResponseData?,
)

@JsonClass(generateAdapter = true)
data class ApiStartChatResponseData(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "type")
    val type: Int?,
    @Json(name = "users")
    val users: List<Int>?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "updatedAt")
    val updatedAt: String?,
    @Json(name = "createdAt")
    val createdAt: String?,
)