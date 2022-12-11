package com.vfadin.events.domain.entity

import com.squareup.moshi.Json
import com.vfadin.events.data.entity.home.ApiRoom

//data class ApiRoom(
//    @Json(name = "id")
//    val id: String?,
//    @Json(name = "name")
//    val name: String?,
//    @Json(name = "description")
//    val description: String?,
//    @Json(name = "avatar")
//    val avatar: String?,
//    @Json(name = "last_message_id")
//    val lastMessageId: Int?,
//    @Json(name = "type")
//    val type: String?,
//    @Json(name = "users")
//    val users: List<Int>?,
//    @Json(name = "createdAt")
//    val createdAt: String?,
//    @Json(name = "updatedAt")
//    val updatedAt: String?
//)

//ApiRoom to Chat
fun ApiRoom.toChat() = Chat(
    id = id ?: -1,
    name = name ?: "",
    description = description ?: "",
    avatar = avatar ?: "",
    lastMessageId = lastMessageId ?: -1,
    type = type ?: -1,
    members = users ?: emptyList(),
    createdAt = createdAt ?: "",
    updatedAt = updatedAt ?: ""
)


data class Chat(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val avatar: String = "",
    val lastMessageId: Int = -1,
    val type: Int = -1,
    val members: List<Int> = emptyList(),
    val createdAt: String = "",
    val updatedAt: String = "",
    val lastMessage: Message = Message(),
    val avatarUrl: String = ""
)
