package com.example.chelasmultiplayerpokerdice.lobbyCreation

import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.domain.Player

@Composable
fun LobbyCreation(viewModel: LobbyCreationViewModel, navigator: LobbyCreationNavigation) {
    when (val currentState = viewModel.state) {
        is LobbyCreationState.Idle -> {
            InitialLobbyCreationView(
                goBackFunction = { navigator.goToLobbiesScreen() },
                onCreateLobby = { name, description, maxUsers, rounds ->
                    viewModel.createLobby(
                        id = 0,
                        name = name,
                        hostId = 0, // placeholder host id — set appropriately from user/session
                        description = description,
                        rounds = rounds,
                        minUsers = 2,
                        maxUsers = maxUsers,
                        minCreditToParticipate = 0,
                        playersCount = 1,
                        players = emptyList<Player>()
                    )
                }
            )
        }

        is LobbyCreationState.Loading -> LoadingLobbyCreationView()

        is LobbyCreationState.Success -> navigator.goToLobbyDetailsScreen(currentState.newLobbyId)

        is LobbyCreationState.Error -> androidx.compose.material3.Text(text = currentState.message)
    }
}
