package com.vfadin.events.domain.entity

data class User(
    val id: Long,
    val name: String,
    val avatar: String,
    val isSelected: Boolean = false
)
