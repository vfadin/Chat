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
        val options = IO.Options()
        options.forceNew = true
        options.reconnectionAttempts = Int.MAX_VALUE
        options.timeout = 10000
        options.extraHeaders =
            mapOf("token" to listOf("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IjFAcS5ydSIsImlkIjo5LCJ1c2VyX25hbWUiOiJ6eGN2Ym5tIiwiYXZhdGFyIjoiaHR0cDovLzE5NC4xNDcuMTE1LjIwNTozMDAwL2FwaS9maWxlL2F2YXRhci85LmpwZyIsInN0YXR1cyI6IiIsIm5hbWUiOiIiLCJzdXJuYW1lIjoiIiwiaWF0IjoxNjc2ODE5MDE1LCJleHAiOjE2Nzk0MTEwMTV9.0lPVHXMYIapH_8NXUuiAKRNAg8iYI-5gRBEuYadE6TQ"))
        val socket = IO.socket("http://194.147.115.205:3000", options)
        socket.connect()
        socket.emit("join", 1)
        socket.on("message") {
            println(it.first() as JSONObject)
        }
//        socket.emit("message", "Hello, server!")
        socket.on("error") { args ->
            println("Received event in my-room: $args")
        }
        setContent {
            navController = rememberNavController()
            EventsTheme {
                Navigation(navController = navController, startDestination = startDestination)
            }
        }
    }
}