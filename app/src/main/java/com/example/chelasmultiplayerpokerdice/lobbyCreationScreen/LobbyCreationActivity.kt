package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl

class LobbyCreationActivity : ComponentActivity() {

    private val lobbyCreationService: LobbyCreationService = LobbyCreationServiceImpl()
    private val lobbyCreationNavigation: LobbyCreationNavigation by lazy {
        NavigationIntentImpl(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LobbyCreation(
                service = lobbyCreationService,
                navigator = lobbyCreationNavigation
            )
        }


    }

}
