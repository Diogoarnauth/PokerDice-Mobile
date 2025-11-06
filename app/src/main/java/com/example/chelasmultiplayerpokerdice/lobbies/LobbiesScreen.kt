package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun LobbiesScreen(viewModel: LobbiesViewModel, navigator: LobbiesNavigation) {
    val currentState by viewModel.state.collectAsState()
    when (currentState) {
        is LobbiesScreenState.Loading -> {
            Text("A carregar lobbies...") // TODO: Usar um LoadingView comum
        }

        is LobbiesScreenState.Success -> {
            LobbiesView(
                lobbies = (currentState as LobbiesScreenState.Success).lobbies,                goBackTitleScreenFunction = { navigator.goToTitleScreen() },
                createLobbyFunction = { navigator.goToLobbyCreationScreen() },
                selectLobbyFunction = { lobby -> navigator.goToLobbyDetailsScreen(lobby.id) }
            )
        }

        is LobbiesScreenState.Error -> {
            Text("Erro: ${(currentState as LobbiesScreenState.Error).error.message}")        }
    }
}