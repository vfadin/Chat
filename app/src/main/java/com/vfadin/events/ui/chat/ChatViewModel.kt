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
import io.socket.client.Socket
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: IChatRepo,
) : ViewModel() {

    var appBarState by mutableStateOf(ChatAppBarState())
    var messageState by mutableStateOf(listOf<Message>())
    private var isInitialized = false

    fun init(chatId: Int) {
        appBarState = appBarState.copy(
            name = "John Doe",
            avatarUrl = "https://randomuser.me/api/portraits/men/1.jpg",
            isOnline = true,
        )
        if (!isInitialized) {
            isInitialized = true
            viewModelScope.launch {
                getAllMessages(chatId)
                repo.startSocket(chatId).collect {
                    messageState = messageState.plus(it)
                }
            }
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