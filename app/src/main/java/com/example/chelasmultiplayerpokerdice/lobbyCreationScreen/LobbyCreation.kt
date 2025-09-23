package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LobbyCreation(service: LobbyCreationService, navigator: LobbyCreationNavigation) {
    val coroutineScope = rememberCoroutineScope()
    InitialLobbyCreationView(
        onCreateLobby = { lobbyName: String, description: String, maxPlayers: Int, rounds: Int ->
            coroutineScope.launch {
                val newLobbyId = service.createLobby(
                    id = 0,
                    name = lobbyName,
                    owner = "ownerName", // Replace with actual owner
                    description = description,
                    rounds = rounds,
                    isPrivate = false,
                    password = null,
                    playersCount = 1,
                    maxPlayers = maxPlayers,
                    players = emptyList()
                )
                navigator.goToLobbyDetailsScreen(1) // Replace with newLobbyId when backend is ready
            }
        },
        goBackFunction = { navigator.goToLobbiesScreen() }
    )
}