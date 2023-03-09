package com.vfadin.events.ui.home.newChat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.User
import com.vfadin.events.domain.repo.IHomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewChatViewModel @Inject constructor(
    private val repo: IHomeRepo,
) : ViewModel() {

    var users by mutableStateOf(listOf<User>())
    var navController: NavHostController? = null
    var isInitialized = false

    fun init(navHostController: NavHostController) {
        if (!isInitialized) {
            navController = navHostController
            isInitialized = true
        }
    }

    init {
        viewModelScope.launch {
            getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            when (val response = repo.getUsers()) {
                is RequestResult.Success -> {
                    users = response.result
                }
                is RequestResult.Error -> {}
            }
        }
    }

    fun createChat() {
        viewModelScope.launch {
            when (repo.createChat(users.filter { it.isSelected }.map { it.id })) {
                is RequestResult.Success -> {
                    navController?.popBackStack()
                    navController?.navigate("home")
                }
                is RequestResult.Error -> {}
            }
        }
    }
}