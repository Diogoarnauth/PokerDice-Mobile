package com.example.chelasmultiplayerpokerdice.lobby

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.ui.theme.common.LoadingView

@Composable
fun LobbyScreen(
    viewModel: LobbyViewModel,
    navigator: LobbyNavigation,
    user: AuthenticatedUser,
    lobbyId: Int
) {
    LaunchedEffect(lobbyId, user.token) {
        viewModel.loadLobby(lobbyId, user.token)
    }

    val gameIdToNavigate by viewModel.navigateToGame.collectAsState()

    LaunchedEffect(gameIdToNavigate) {
        if (gameIdToNavigate != null) {
            // Se detetámos um jogo, vamos para lá!
            navigator.goToGameScreen(user, lobbyId)
        }
    }
    val currentState by viewModel.state.collectAsState()

    when (val state = currentState) {
        is LobbyScreenState.Loading -> LoadingView("A carregar informações...")

        is LobbyScreenState.Success -> LobbyScreenView(
            lobby = state.lobby,
            players = state.players,
            currentUserId = state.myId,
            goBackTitleScreenFunction = { navigator.goToTitleScreen(user) },
            onAbandon = {
                viewModel.onAbandon(lobbyId, user.token)
                navigator.goToTitleScreen(user)
            },
            onJoinLobby = {
                viewModel.onJoin(lobbyId, user.token)
            },
            onStartGame = {
                viewModel.onStartGame(lobbyId, user.token)
            }
        )

        is LobbyScreenState.Error -> Text("Erro: ${state.message}")
    }
}