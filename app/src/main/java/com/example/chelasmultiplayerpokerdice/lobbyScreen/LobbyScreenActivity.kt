package com.example.chelasmultiplayerpokerdice.lobbyScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl

class LobbyScreenActivity : ComponentActivity() {

    private val lobbyScreenService: LobbyScreenService = LobbyScreenFakeServiceImpl()
    private val lobbyNavigation: LobbyScreenNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: LobbyScreenViewModel =
                viewModel(factory = LobbyScreenViewModelFactory(lobbyScreenService))

            when (val currentState = viewModel.state) {
                is LobbyScreenState.Loading -> {
                    Text("A carregar informações do lobby...")
                }

                is LobbyScreenState.Success -> {
                    LobbyScreenView(
                        lobby = currentState.lobby,
                        onAbandon = { lobbyNavigation.goToLobbiesScreen() },
                        onStartGame = { lobbyNavigation.goToGameScreen() }
                    )
                }

                is LobbyScreenState.Error -> {
                    Text(text = currentState.message)
                }
            }
        }
    }
}
