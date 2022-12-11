package com.vfadin.events.ui.profile.changeFieldScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.vfadin.events.domain.RequestResult
import com.vfadin.events.domain.repo.IProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeFieldViewModel @Inject constructor(
    private val repo: IProfileRepo
) : ViewModel() {

    var fieldValue by mutableStateOf("")
    var navController: NavHostController? = null
    var isInitialized = false

    fun init(navHostController: NavHostController) {
        if(!isInitialized) {
            navController = navHostController
            isInitialized = true
        }
    }

    fun changeNickname() {
        viewModelScope.launch {
            when (val response = repo.changeNickname(fieldValue)) {
                is RequestResult.Success -> {
                    println(response.result)
                    navController?.popBackStack()
                    navController?.navigate("profile/true")
                }
                is RequestResult.Error -> {}
            }
        }
    }

    fun changeName() {
        viewModelScope.launch {
            when (val response = repo.changeName(fieldValue)) {
                is RequestResult.Success -> {
                    navController?.navigateUp()
                }
                is RequestResult.Error -> {}
            }
        }
    }
}