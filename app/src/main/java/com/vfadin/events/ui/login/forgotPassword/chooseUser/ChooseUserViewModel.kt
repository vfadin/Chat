//package com.vfadin.events.ui.login.forgotPassword.chooseUser
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.vfadin.events.domain.repo.IForgotPasswordRepo
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.filterNotNull
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ChooseUserViewModel @Inject constructor(
//    private val forgotPasswordRepo: IForgotPasswordRepo
//) : ViewModel() {
//
//    var token = ""
//    var chosenUserId = -1
//        private set
//    val isError = mutableStateOf(false)
//    val isRefreshing = mutableStateOf(false)
//
//    private val _userResetPasswordStateFlow = MutableStateFlow<List<UserResetPassword>?>(null)
//    val userResetPasswordFlow = _userResetPasswordStateFlow.asStateFlow().filterNotNull()
//
//    fun getUsers() {
//        isRefreshing.value = true
//        viewModelScope.launch {
//            val response = forgotPasswordRepo.getResetPasswordUsers(token)
//            if (response.isEmpty()) {
//                isError.value = true
//            }
//            _userResetPasswordStateFlow.value = response
//        }
//        isRefreshing.value = false
//    }
//
//    fun onUserChosen(userId: Int) {
//        chosenUserId = userId
//    }
//}