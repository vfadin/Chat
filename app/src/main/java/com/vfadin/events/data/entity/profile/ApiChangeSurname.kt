package com.vfadin.events.data.entity.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiChangeSurname(
    @Json(name = "surname")
    val surname: String
)
