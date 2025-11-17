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
                onCreateLobby = { name, description, maxPlayers, rounds ->
                    viewModel.createLobby(
                        name = name,
                        description = description,
                        maxUsers = maxPlayers,
                        rounds = rounds,
                        hostToken = user.token
                    )
                }
            )
        }

        is LobbyCreationState.Loading -> LoadingLobbyCreationView()

        is LobbyCreationState.Success -> {
            navigator.goToLobbyDetailsScreen(user, currentState.newLobbyId)
            (context as? Activity)?.finish()
        }

        is LobbyCreationState.Error -> androidx.compose.material3.Text(text = currentState.message)
    }
}
