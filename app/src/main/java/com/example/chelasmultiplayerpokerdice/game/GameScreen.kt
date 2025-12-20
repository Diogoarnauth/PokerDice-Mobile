package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.ui.theme.common.ErrorAlert
import com.example.chelasmultiplayerpokerdice.ui.theme.common.LoadingView

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    navigator: GameNavigation,
    user: AuthenticatedUser,
    lobbyId: Int
) {
    LaunchedEffect(lobbyId, user.token) {
        viewModel.loadGame(lobbyId, user.token)
    }

    val currentState by viewModel.state.collectAsState()

    when (val state = currentState) {
        is GameScreenState.Loading -> {
            LoadingView(text = "A preparar o jogo...")
        }

        is GameScreenState.Playing -> {
            Log.d(TAG, "ENTREI NO PLAYING")
            GameView(
                state = state.gameState,
                onDieClicked = { dieId -> viewModel.onDieClicked(dieId) },
                onRollClicked = { viewModel.onRollClicked(user.token) },
                onEndTurnClicked = { viewModel.onEndTurnClicked(user.token) }
            )
        }

        is GameScreenState.RoundOver -> {
            GameView(
                state = state.gameState,
                onDieClicked = { },
                onRollClicked = { },
                onEndTurnClicked = { }
            )

            RoundOverDialog(
                winnerName = state.winner.name,
                onDismiss = {
                    viewModel.onStartNextRound()
                }
            )
        }

        is GameScreenState.GameOver -> {

            GameView(
                state = state.gameState,
                onDieClicked = { },
                onRollClicked = { },
                onEndTurnClicked = { }
            )

            GameOverDialog(
                winnersNames = state.winners.map { it.name },
                onDismiss = {
                    navigator.goToTitleScreen(user)
                }
            )
        }

        is GameScreenState.Error -> {
            ErrorAlert(
                title = "Erro de Jogo",
                message = state.message,
                buttonText = "Voltar ao Menu",
                onDismiss = { navigator.goToTitleScreen(user) }
            )
        }
    }
}