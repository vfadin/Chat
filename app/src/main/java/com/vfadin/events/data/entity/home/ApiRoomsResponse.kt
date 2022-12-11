package com.vfadin.events.data.entity.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRoomsResponse(
    @Json(name = "data")
    val data: List<ApiRoom>?
)

@JsonClass(generateAdapter = true)
data class ApiRoom(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "last_message_id")
    val lastMessageId: Int?,
    @Json(name = "type")
    val type: Int?,
    @Json(name = "users")
    val users: List<Int>?,
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "updatedAt")
    val updatedAt: String?
)