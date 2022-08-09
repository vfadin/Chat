package com.vfadin.chat

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.vfadin.chat.ui.home.Home
import com.vfadin.chat.ui.PermissionDeniedScreen

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {

    private val permissionsList = mutableListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val permissionState = rememberMultiplePermissionsState(permissionsList)
            if (permissionState.allPermissionsGranted) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { Home() }
                }
            } else {
                PermissionDeniedScreen(permissionState)
            }
        }
    }
}

