package com.example.chelasmultiplayerpokerdice.lobbyScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl

class LobbyScreenActivity : ComponentActivity() {

    private val lobbyScreenService: LobbyScreenService = LobbyScreenServiceImpl()
    private val lobbyNavigation: LobbyScreenNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LobbyScreen(
                service = lobbyScreenService,
                navigator = lobbyNavigation
            )
        }
    }
}
