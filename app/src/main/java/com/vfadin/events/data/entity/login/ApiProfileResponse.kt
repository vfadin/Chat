package com.vfadin.events.data.entity.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vfadin.events.domain.entity.Profile

@JsonClass(generateAdapter = true)
data class ApiProfileResponse(
    @Json(name = "data")
    val data: ApiProfile?,
)

@JsonClass(generateAdapter = true)
data class ApiProfile(
    @Json(name = "id")
    val id: String?,
    @Json(name = "user_name")
    val userName: String?,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "name")
    val firstName: String?,
    @Json(name = "surname")
    val lastName: String?,
)

fun ApiProfile.toProfile() = Profile(
    email = email ?: "",
//    phone = phone ?: "",
    firstName = firstName ?: "",
    lastName = lastName ?: "",
    nickname = userName ?: "",
    avatar = avatar ?: "",
)