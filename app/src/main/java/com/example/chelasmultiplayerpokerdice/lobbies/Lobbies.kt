package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.compose.runtime.Composable

@Composable
fun Lobbies(viewModel: LobbiesViewModel, navigator: LobbiesNavigation) {
    when (val currentState = viewModel.state) {
        is LobbiesScreenState.Loading -> {
            androidx.compose.material3.Text("A carregar lobbies...")
        }

        is LobbiesScreenState.Success -> {
            LobbiesView(
                lobbies = currentState.lobbies,
                goBackTitleScreenFunction = { navigator.goToTitleScreen() },
                createLobbyFunction = { navigator.goToLobbyCreationScreen() },
                selectLobbyFunction = { lobby -> navigator.goToLobbyDetailsScreen(lobby.id) }
            )
        }

        is LobbiesScreenState.Error -> {
            androidx.compose.material3.Text("Erro: ${currentState.error.message}")
        }
    }
}
