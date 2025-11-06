package com.example.chelasmultiplayerpokerdice.lobby

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun LobbyScreen(viewModel: LobbyScreenViewModel, navigator: LobbyNavigation) {
    val currentState by viewModel.state.collectAsState()
    when (currentState) {
        is LobbyScreenState.Loading -> Text("A carregar informações do lobby...")

        is LobbyScreenState.Success -> LobbyScreenView(
            lobby = (currentState as LobbyScreenState.Success).lobby,
            onAbandon = {
                viewModel.onAbandon()
                navigator.goToTitleScreen() },
            onStartGame = { navigator.goToGameScreen() }
        )

        is LobbyScreenState.Error -> Text("Erro: ${(currentState as LobbyScreenState.Error).message}")
    }
}
