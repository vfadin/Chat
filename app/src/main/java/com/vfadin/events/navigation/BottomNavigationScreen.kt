package com.vfadin.events.navigation

import androidx.annotation.StringRes
import com.vfadin.events.R

sealed class BottomNavigationScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: Int
) {
    object Home : BottomNavigationScreen("home", R.string.home, R.drawable.ic_home)
    object Profile : BottomNavigationScreen("profile/{arg}", R.string.profile, R.drawable.ic_user_fill)
}