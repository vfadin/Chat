package com.vfadin.events.domain.entity.login

data class Captcha(
    val key: String,
    val image: String,
    val value: String
)
