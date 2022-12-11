package com.vfadin.events.data.entity.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMessagesResponse(
    @Json(name = "data")
    val data: List<ApiMessage>?
)

@JsonClass(generateAdapter = true)
data class ApiMessage(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "type")
    val type: Int?,
    @Json(name = "from")
    val from: Int?,
    @Json(name = "read_status")
    val read_status: Boolean?,
    @Json(name = "room_id")
    val room_id: Int?,
    @Json(name = "created_at")
    val created_at: String?,
    @Json(name = "updated_at")
    val updated_at: String?
)