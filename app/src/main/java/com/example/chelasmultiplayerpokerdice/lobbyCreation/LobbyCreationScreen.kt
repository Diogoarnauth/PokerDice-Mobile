package com.example.chelasmultiplayerpokerdice.lobbyCreation

import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

@Composable
fun LobbyCreation(viewModel: LobbyCreationViewModel, navigator: LobbyCreationNavigation, user: AuthenticatedUser) {
    when (val currentState = viewModel.state) {
        is LobbyCreationState.Idle -> {

            InitialLobbyCreationView(
                goBackFunction = { navigator.goToLobbiesScreen(user) },
                onCreateLobby = { name, description, maxPlayers, rounds ->
                    viewModel.createLobby(
                        name = name,
                        description = description,
                        maxUsers = maxPlayers,
                        rounds = rounds,
                        hostToken = user.token
                    )
                }
            )
        }

        is LobbyCreationState.Loading -> LoadingLobbyCreationView()

        is LobbyCreationState.Success -> navigator.goToLobbyDetailsScreen(user, currentState.newLobbyId)

        is LobbyCreationState.Error -> androidx.compose.material3.Text(text = currentState.message)
    }
}
