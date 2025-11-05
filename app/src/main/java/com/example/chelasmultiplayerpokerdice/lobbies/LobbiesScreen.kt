package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

@Composable
fun LobbiesScreen(viewModel: LobbiesViewModel, navigator: LobbiesNavigation) {
    when (val currentState = viewModel.state) {
        is LobbiesScreenState.Loading -> {
            Text("A carregar lobbies...") // TODO: Usar um LoadingView comum
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
            Text("Erro: ${currentState.error.message}") // TODO: Usar um ErrorView comum
        }
    }
}