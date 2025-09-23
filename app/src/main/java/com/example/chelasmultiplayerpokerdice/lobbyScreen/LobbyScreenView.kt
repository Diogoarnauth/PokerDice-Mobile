package com.example.chelasmultiplayerpokerdice.lobbyScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ---- Test Tags ----
const val LOBBYSCREEN_VIEW_TAG = "Lobby View"
const val LOBBYSCREEN_LOBBY_INFO = "Lobby Information"
const val LOBBYSCREEN_PLAYERS_LIST = "Lobby Players List"
const val LOBBYSCREEN_ABANDON_BUTTON = "Abandon Button"
const val LOBBYSCREEN_STARTGAME_BUTTON = "Start Game Button"
const val TITLESCREEN_BUTTON = "Button to return to Title Screen"

// ---- Data Models ----
data class Lobby(
    val id: Int,
    val name: String,
    val owner: String,
    val description: String,
    val rounds: Int,
    val isPrivate: Boolean,
    val password: String?,
    val playersCount: Int,
    val maxPlayers: Int,
    val players: List<Player>
)

data class Player(val id: Int, val name: String)

// ---- Composable ----
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreenView(
    lobby: Lobby,
    onAbandon: () -> Unit,
    onStartGame: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LOBBYSCREEN_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🏠 Lobby: ${lobby.name}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Lobby Information
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(LOBBYSCREEN_LOBBY_INFO),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = lobby.description,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start
                    )
                    Text("👑 Dono: ${lobby.owner}")
                    Text("Jogadores: ${lobby.playersCount}/${lobby.maxPlayers}")
                    Text("Número de rondas: ${lobby.rounds}")
                    Text(
                        if (lobby.isPrivate) "🔒 Privado" else "🌍 Público",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Players List
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(LOBBYSCREEN_PLAYERS_LIST)
                ) {
                    Text(
                        text = "Jogadores (${lobby.players.size}/${lobby.maxPlayers}):",
                        style = MaterialTheme.typography.titleMedium
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(lobby.players) { player ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(player.name, style = MaterialTheme.typography.bodyMedium)
                                    Text("ID: ${player.id}", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onAbandon,
                        modifier = Modifier
                            .weight(1f)
                            .testTag(LOBBYSCREEN_ABANDON_BUTTON),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Abandonar")
                    }

                    Button(
                        onClick = onStartGame,
                        modifier = Modifier
                            .weight(1f)
                            .testTag(LOBBYSCREEN_STARTGAME_BUTTON),
                        enabled = lobby.players.size >= 2 // só começa se houver jogadores suficientes
                    ) {
                        Text("Iniciar Jogo")
                    }
                }
            }
        }
    )
}

// ---- Preview ----
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LobbyScreenPreview() {
    val fakeLobby = Lobby(
        id = 1,
        name = "Poker Masters",
        owner = "Renata",
        description = "Lobby para testar a sorte 🎲",
        rounds = 12,
        isPrivate = false,
        password = null,
        playersCount = 3,
        maxPlayers = 4,
        players = listOf(
            Player(1, "Renata"),
            Player(2, "Diogo"),
            Player(3, "Humberto")
        )
    )

    LobbyScreenView(
        lobby = fakeLobby,
        onAbandon = {},
        onStartGame = {}
    )
}