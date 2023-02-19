package com.vfadin.events.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val socket: Socket
) : ViewModel() {

    var appBarState by mutableStateOf(ChatAppBarState())
    var chatId = -1

    init {
        appBarState = appBarState.copy(
            name = "John Doe",
            avatarUrl = "https://randomuser.me/api/portraits/men/1.jpg",
            isOnline = true,
        )
        socket.connect()
        socket.emit("join", chatId)
        socket.on("message") { args ->
            val data = args[0] as JSONObject
            println("Received event in my-room: $data")
        }
        socket.on("error") { args ->
            println("Received event in my-room: $args")
        }
    }
}