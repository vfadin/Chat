package com.vfadin.events.domain.entity.login

data class LoginResponse(
    val resultCode: String = "",
    val message: String = "",
    val token: String = "",
    val tokenType: String = ""
)