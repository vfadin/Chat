package com.vfadin.events.data.entity.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vfadin.events.data.entity.login.ApiProfile

@JsonClass(generateAdapter = true)
data class ApiGetUsers(
    @Json(name = "data")
    val data: List<ApiProfile>?
)
