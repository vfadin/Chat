package com.vfadin.events.domain.entity

@kotlinx.serialization.Serializable
data class Profile(
    val id: Int = -1,
    val email: String = "",
    val phone: String = "",
    val birthdayAt: String = "",
    val caption: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val emailVerifiedAt: String = "",
    val nickname: String = "",
    val avatar: String = "",
    val address: String = ""
)