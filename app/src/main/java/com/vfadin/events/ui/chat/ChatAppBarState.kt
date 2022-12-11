package com.vfadin.events.ui.chat

data class ChatAppBarState(
    val name: String = "",
    val avatarUrl: String = "",
    val memberCount: Int = 0,
    val isOnline: Boolean = false
)
