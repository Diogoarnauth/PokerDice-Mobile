package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import androidx.compose.runtime.Composable

@Composable
fun LobbyCreation(viewModel: LobbyCreationViewModel, navigator: LobbyCreationNavigation) {
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

        is LobbyCreationState.Loading -> LoadingLobbyCreationView()

        is LobbyCreationState.Success -> navigator.goToLobbyDetailsScreen(currentState.newLobbyId)

        is LobbyCreationState.Error -> androidx.compose.material3.Text(text = currentState.message)
    }
}
