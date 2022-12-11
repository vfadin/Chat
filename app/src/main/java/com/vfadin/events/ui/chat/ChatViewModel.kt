package com.vfadin.events.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ChatViewModel @Inject constructor(

) : ViewModel() {

    var appBarState by mutableStateOf(ChatAppBarState())

    init {
        appBarState = appBarState.copy(
            name = "John Doe",
            avatarUrl = "https://randomuser.me/api/portraits/men/1.jpg",
            isOnline = true,
        )
    }
}