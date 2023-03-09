package com.vfadin.events.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Message
import com.vfadin.events.domain.repo.IChatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: IChatRepo,
) : ViewModel() {

    var chatState by mutableStateOf(ChatState())
    var messageState by mutableStateOf(listOf<Message>())
    private var isInitialized = false

    fun init(chatId: Int, title: String, memberCount: Int) {
        chatState = chatState.copy(
            name = title,
            roomId = chatId,
            memberCount = memberCount
        )
        if (!isInitialized) {
            isInitialized = true
            viewModelScope.launch {
                getCurrentUser()
                getAllMessages(chatId)
                if (messageState.size > 1) chatState.listState.scrollToItem(messageState.size - 1)
                repo.startSocket(chatId).collect {
                    messageState = messageState.plus(it)
                }
            }
        }
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.MessageTextChanged -> chatState = chatState.copy(message = event.text)
            ChatEvent.OnAttachClicked -> {}
            ChatEvent.OnSendClicked -> sendMessage()
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            if (chatState.message.isNotEmpty()) {
                repo.sendMessage(chatState.roomId, chatState.message)
                getAllMessages(chatState.roomId)
                chatState = chatState.copy(message = "")
            }
        }
    }

    private suspend fun getCurrentUser() {
        when (val response = repo.getCurrentUser()) {
            is RequestResult.Success -> {
                chatState = chatState.copy(currentUser = response.result)
            }
            is RequestResult.Error -> {}
        }
    }

    private suspend fun getAllMessages(chatId: Int) {
        when (val response = repo.getAllMessages(chatId)) {
            is RequestResult.Success -> {
                messageState = response.result
            }
            is RequestResult.Error -> {}
        }
    }
}