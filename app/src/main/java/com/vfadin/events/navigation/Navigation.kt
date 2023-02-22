package com.vfadin.events.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.vfadin.events.ui.chat.ChatScreen
import com.vfadin.events.ui.chat.ChatViewModel
import com.vfadin.events.ui.home.HomeScreen
import com.vfadin.events.ui.home.HomeViewModel
import com.vfadin.events.ui.home.newChat.NewChatScreen
import com.vfadin.events.ui.home.newChat.NewChatViewModel
import com.vfadin.events.ui.login.AccExistScreen
import com.vfadin.events.ui.login.LoginScreen
import com.vfadin.events.ui.login.LoginViewModel
import com.vfadin.events.ui.login.forgotPassword.ForgotPasswordScreen
import com.vfadin.events.ui.login.forgotPassword.ForgotPasswordViewModel
import com.vfadin.events.ui.login.forgotPassword.newPassword.NewPasswordScreen
import com.vfadin.events.ui.login.forgotPassword.newPassword.NewPasswordViewModel
import com.vfadin.events.ui.profile.ProfileScreen
import com.vfadin.events.ui.profile.ProfileViewModel
import com.vfadin.events.ui.profile.changeFieldScreen.ChangeFieldScreen
import com.vfadin.events.ui.profile.changeFieldScreen.ChangeFieldViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController, viewModel)
        }
        composable("forgot_password") {
            val viewModel = hiltViewModel<ForgotPasswordViewModel>()
            ForgotPasswordScreen(viewModel, navController)
        }
        composable(
            route = "acc_exist",
        ) {
            AccExistScreen(navController)
        }
        composable(
            route = "new_password/{token}",
            deepLinks = listOf(navDeepLink {
                uriPattern = "http://194.58.100.27/api/reset/{token}"
            })
        ) {
            val viewModel = hiltViewModel<NewPasswordViewModel>()
            it.arguments?.getString("token")?.let { token ->
                viewModel.token = token
            }
            NewPasswordScreen(viewModel, navController)
        }
        composable("change_field/{title}") {
            it.arguments?.getString("title")?.let { title ->
                val viewModel = hiltViewModel<ChangeFieldViewModel>()
                viewModel.init(navController)
                ChangeFieldScreen(title, navController, viewModel)
            }
        }
        composable("new_chat") {
            val viewModel = hiltViewModel<NewChatViewModel>()
            viewModel.init(navController)
            NewChatScreen(viewModel)
        }
        composable(BottomNavigationScreen.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            viewModel.init(navController)
            it.arguments?.getBoolean("arg")?.let {
                viewModel.refresh()
            }
            HomeScreen(viewModel, navController)
        }
        composable(BottomNavigationScreen.Profile.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            viewModel.init(navController)
            it.arguments?.getBoolean("arg")?.let {
                viewModel.refresh()
            }
            ProfileScreen(viewModel, navController)
        }
        composable("chat_screen/{id}/{title}/{memberCount}") {
            it.arguments?.getString("id")?.let { id ->
                it.arguments?.getString("title")?.let { title ->
                    it.arguments?.getString("memberCount")?.let { memberCount ->
                        val viewModel = hiltViewModel<ChatViewModel>()
                        viewModel.init(
                            chatId = id.toIntOrNull() ?: -1,
                            title = title,
                            memberCount = memberCount.toIntOrNull() ?: -1)
                        ChatScreen(viewModel, navController)
                    }
                }
            }
        }
    }
}