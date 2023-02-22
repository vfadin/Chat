package com.vfadin.events.ui.chat

sealed class ChatEvent {
    data class MessageTextChanged(val text: String) : ChatEvent()
    object OnSendClicked : ChatEvent()
    object OnAttachClicked : ChatEvent()
}
