package com.vfadin.events.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Chat
import com.vfadin.events.domain.repo.IHomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: IHomeRepo
) : ViewModel() {
    private var chatList = listOf<Chat>()
    private val _chatsStateFlow = MutableStateFlow<List<Chat>?>(null)
    val chatsStateFlow = _chatsStateFlow.asStateFlow().filterNotNull()

    var navController: NavHostController? = null
    var isInitialized = false
    var searchState = mutableStateOf("")
    var isSearchVisible = mutableStateOf(false)

    fun init(navHostController: NavHostController) {
        if (!isInitialized) {
            navController = navHostController
            isInitialized = true
        }
    }

    fun refresh() {
        viewModelScope.launch {
            getChats()
        }
    }

    init {
        viewModelScope.launch {
            getChats()
        }
    }

    suspend fun getChats() {
        when (val response = repo.getChats()) {
            is RequestResult.Success -> {
                chatList = response.result
                _chatsStateFlow.value = response.result
            }
            is RequestResult.Error -> {}
        }
    }

    fun navigateToNewChat() {
        navController?.navigate("new_chat")
    }

    fun search() {
        _chatsStateFlow.value = chatList.filter {
            it.name.contains(searchState.value, true)
        }
    }
}