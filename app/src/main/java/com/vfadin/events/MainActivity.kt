package com.vfadin.events

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vfadin.events.navigation.Navigation
import com.vfadin.events.ui.theme.EventsTheme
import com.vfadin.events.util.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import org.json.JSONObject
import java.util.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navController.handleDeepLink(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var startDestination = "home"
        val sharedPrefs = SharedPrefs(this)
        Calendar.getInstance().timeInMillis.let {
            if (it > sharedPrefs.restoreExpirationDate()) {
                startDestination = "login"
            }
        }
        setContent {
            navController = rememberNavController()
            EventsTheme {
                Navigation(navController = navController, startDestination = startDestination)
            }
        }
    }
}