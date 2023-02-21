package com.vfadin.events.ui.chat

import com.vfadin.events.domain.entity.Profile

data class ChatState(
    val name: String = "",
    val avatarUrl: String = "",
    val memberCount: Int = 0,
    val isOnline: Boolean = false,
    val currentUser: Profile = Profile()
)