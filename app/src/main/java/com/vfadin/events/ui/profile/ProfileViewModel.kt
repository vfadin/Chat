package com.vfadin.events.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.entity.Profile
import com.vfadin.events.domain.repo.IProfileRepo
import com.vfadin.events.util.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: IProfileRepo,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var profileState by mutableStateOf(Profile())
    var navController: NavHostController? = null
    var isInitialized = false

    fun init(navHostController: NavHostController) {
        if (!isInitialized) {
            navController = navHostController
            isInitialized = true
        }
    }

    fun refresh() {
        getProfile()
    }

    init {
        viewModelScope.launch {
            getProfile()
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.ChangeEmailClicked -> {
                isInitialized = false
                navController?.navigate("change_field/Email")
            }
            ProfileEvent.ChangeNameClicked -> {
                isInitialized = false
                navController?.navigate("change_field/Name")
            }
            ProfileEvent.ChangeNicknameClicked -> {
                isInitialized = false
                navController?.navigate("change_field/Nickname")
            }
            ProfileEvent.LogoutClicked -> {
                sharedPrefs.clear()
                navController?.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            when (val response = repo.getProfile()) {
                is RequestResult.Success -> {
                    profileState = response.result
                }
                is RequestResult.Error -> {}
            }
        }
    }
}

////            firstName = "Vladislav",
////            lastName = "Fadin",
////            nickname = "vfadin",
////            email = "v@mail.ru",
////            avatar = "https://avatars.githubusercontent.com/u/1016365?v=4"