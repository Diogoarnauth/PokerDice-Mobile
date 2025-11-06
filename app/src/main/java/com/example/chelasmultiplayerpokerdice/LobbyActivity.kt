package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.lobby.LobbyFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobby.LobbyNavigation
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreen
import com.example.chelasmultiplayerpokerdice.lobby.LobbyService
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreenState
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreenView
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreenViewModel
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreenViewModelFactory


const val LOBBY_ID_EXTRA = "LOBBY_ID"

class LobbyActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val lobbyNavigation: LobbyNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lobbyId = intent.getIntExtra(LOBBY_ID_EXTRA, -1)
        if (lobbyId == -1) {
            // ID inválido, não devíamos estar aqui. Volta.
            finish()
            return
        }
        enableEdgeToEdge()
        setContent {
            val viewModel: LobbyScreenViewModel =
                viewModel(factory = LobbyScreenViewModelFactory(app.lobbyService, lobbyId))
            LobbyScreen(
                viewModel = viewModel,
                navigator = lobbyNavigation
            )
        }
    }
}