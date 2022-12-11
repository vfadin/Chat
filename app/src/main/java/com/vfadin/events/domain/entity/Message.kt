package com.vfadin.events.domain.entity

import com.vfadin.events.data.entity.home.ApiMessage

fun ApiMessage.toMessage() = Message(
    id = id ?: -1,
    text = content ?: "",
    type = type ?: -1,
    author = from ?: -1,
    read_status = read_status ?: false,
    room_id = room_id ?: -1,
    created_at = created_at ?: "",
    updated_at = updated_at ?: ""
)

data class Message(
    val id: Int = -1,
    val text: String = "",
    val type: Int = -1,
    val author: Int = -1,
    val read_status: Boolean = false,
    val room_id: Int = -1,
    val created_at: String = "",
    val updated_at: String = "",
    val date: String = "",
)
