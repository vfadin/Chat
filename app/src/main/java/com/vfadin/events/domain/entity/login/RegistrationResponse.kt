package com.vfadin.events.domain.entity.login

import com.vfadin.events.data.entity.login.ApiRegistrationResponse

data class RegistrationResponse(
    val message : String = "",
    val errors: List<String> = emptyList(),
)

fun ApiRegistrationResponse.toRegistrationResponse() = RegistrationResponse(
    message = message ?: ""
)