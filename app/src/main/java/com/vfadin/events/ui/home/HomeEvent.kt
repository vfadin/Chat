package com.vfadin.events.ui.home

import com.vfadin.events.domain.entity.Chat

sealed class HomeEvent {
    data class CreateChat(val chat: Chat) : HomeEvent()
}

