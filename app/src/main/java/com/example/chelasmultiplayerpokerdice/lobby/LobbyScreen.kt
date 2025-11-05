package com.example.chelasmultiplayerpokerdice.lobby

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

@Composable
fun LobbyScreen(viewModel: LobbyScreenViewModel, navigator: LobbyNavigation) {
    when (val currentState = viewModel.state) {
        is LobbyScreenState.Loading -> Text("A carregar informações do lobby...")

        is LobbyScreenState.Success -> LobbyScreenView(
            lobby = currentState.lobby,
            onAbandon = { navigator.goToLobbiesScreen() },
            onStartGame = { navigator.goToGameScreen() }
        )

        is LobbyScreenState.Error -> Text("Erro: ${currentState.message}")
    }
}
