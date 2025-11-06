package com.example.chelasmultiplayerpokerdice.lobbyCreation

import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.domain.Player

@Composable
fun LobbyCreation(viewModel: LobbyCreationViewModel, navigator: LobbyCreationNavigation) {
    when (val currentState = viewModel.state) {
        is LobbyCreationState.Idle -> {
            InitialLobbyCreationView(
                goBackFunction = { navigator.goToLobbiesScreen() },
                // Chama a nova função simplificada
                onCreateLobby = { name, description, maxPlayers, rounds ->
                    viewModel.createLobby(
                        name = name,
                        description = description,
                        maxUsers = maxPlayers, // Passa só o que a View sabe
                        rounds = rounds
                    )
                }
            )
        }

        is LobbyCreationState.Loading -> LoadingLobbyCreationView()

        is LobbyCreationState.Success -> navigator.goToLobbyDetailsScreen(currentState.newLobbyId)

        is LobbyCreationState.Error -> androidx.compose.material3.Text(text = currentState.message)
    }
}
