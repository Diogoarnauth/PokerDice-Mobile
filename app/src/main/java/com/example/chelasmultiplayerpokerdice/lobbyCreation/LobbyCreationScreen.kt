package com.example.chelasmultiplayerpokerdice.lobbyCreation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

@Composable
fun LobbyCreation(
    viewModel: LobbyCreationViewModel,
    navigator: LobbyCreationNavigation,
    user: AuthenticatedUser
) {
    val context = LocalContext.current


    when (val currentState = viewModel.state) {
        is LobbyCreationState.Idle -> {
            InitialLobbyCreationView(
                goBackFunction = { navigator.goToLobbiesScreen(user) },
                onCreateLobby = { name, description, minPlayers, maxPlayers, minCredits, rounds ->
                    viewModel.createLobby(
                        name = name,
                        description = description,
                        minUsers = minPlayers,
                        maxUsers = maxPlayers,
                        rounds = rounds,
                        minCreditToParticipate = minCredits,
                        hostToken = user.token // Não esquecer o token aqui!
                    )
                }
            )
        }

        is LobbyCreationState.Loading -> LoadingLobbyCreationView()

        is LobbyCreationState.Success -> {
            navigator.goToLobbiesScreen(user)
            (context as? Activity)?.finish()
        }

        is LobbyCreationState.Error -> {
            InitialLobbyCreationView(
                goBackFunction = { navigator.goToLobbiesScreen(user) },
                onCreateLobby = { name, description, minPlayers, maxPlayers, minCredits, rounds ->
                    viewModel.createLobby(name, description, minPlayers, maxPlayers, minCredits, rounds, user.token)
                }
            )

            androidx.compose.material3.AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                confirmButton = {
                    androidx.compose.material3.TextButton(onClick = { viewModel.clearError() }) {
                        androidx.compose.material3.Text("OK")
                    }
                },
                title = { androidx.compose.material3.Text("Erro") },
                text = { androidx.compose.material3.Text(currentState.message) }
            )
        }
    }
}
