package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl

class LobbyCreationActivity : ComponentActivity() {

    private val service: LobbyCreationService = LobbyCreationFakeServiceImpl()
    private val navigator: LobbyCreationNavigation by lazy { NavigationIntentImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: LobbyCreationViewModel =
                viewModel(factory = LobbyCreationViewModelFactory(service))

            when (val currentState = viewModel.state) {
                is LobbyCreationState.Idle -> {
                    InitialLobbyCreationView(
                        goBackFunction = { navigator.goToLobbiesScreen() },
                        onCreateLobby = { name, description, maxPlayers, rounds ->
                            viewModel.createLobby(
                                id = 0,
                                name = name,
                                owner = "Owner",
                                description = description,
                                rounds = rounds,
                                isPrivate = false,
                                password = null,
                                playersCount = 1,
                                maxPlayers = maxPlayers,
                                players = emptyList()
                            )
                        }
                    )
                }

                is LobbyCreationState.Loading -> {
                    LoadingLobbyCreationView()
                }

                is LobbyCreationState.Success -> {
                    navigator.goToLobbyDetailsScreen(currentState.newLobbyId)
                }

                is LobbyCreationState.Error -> {
                    Text(text = currentState.message)
                }
            }
        }
    }
}
