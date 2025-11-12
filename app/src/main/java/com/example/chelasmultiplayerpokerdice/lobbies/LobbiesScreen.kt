package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.ui.theme.common.LoadingView

@Composable
fun LobbiesScreen(
    viewModel: LobbiesViewModel,
    navigator: LobbiesNavigation,
    user: AuthenticatedUser
) {
    val currentState by viewModel.state.collectAsState()
    when (currentState) {
        is LobbiesScreenState.Loading -> {
            LoadingView("A carregar lobbies...")
        }

        is LobbiesScreenState.Success -> {
            LobbiesView(
                lobbies = (currentState as LobbiesScreenState.Success).lobbies,
                goBackTitleScreenFunction = { navigator.goToTitleScreen(user) },
                createLobbyFunction = { navigator.goToLobbyCreationScreen(user) },
                selectLobbyFunction = { lobby -> navigator.goToLobbyDetailsScreen(user, lobby.id) }
            )
        }

        is LobbiesScreenState.Error -> {
            Text("Erro: ${(currentState as LobbiesScreenState.Error).error.message}")
        }
    }
}