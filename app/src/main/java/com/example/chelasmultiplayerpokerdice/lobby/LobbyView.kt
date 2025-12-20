package com.example.chelasmultiplayerpokerdice.lobby

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.User

const val LOBBYSCREEN_VIEW_TAG = "Lobby View"
const val LOBBYSCREEN_LOBBY_INFO = "Lobby Information"
const val LOBBYSCREEN_PLAYERS_LIST = "Lobby Players List"
const val LOBBYSCREEN_ABANDON_BUTTON = "Abandon Button"
const val LOBBYSCREEN_STARTGAME_BUTTON = "Start Game Button"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreenView(
    lobby: Lobby,
    players: List<User>,
    goBackTitleScreenFunction: () -> Unit, // 1. Adicionado aqui
    onAbandon: () -> Unit,
    onStartGame: () -> Unit,
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
                },
                // 2. Adicionamos o botão de voltar no canto superior direito ou esquerdo
                actions = {
                    IconButton(onClick = goBackTitleScreenFunction) {
                        Icon(Icons.Default.Home, contentDescription = "Voltar ao Menu")
                    }
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
                    Text("👥 Jogadores: ${players.size}/${lobby.maxUsers}")
                    Text("🎲 Número de rondas: ${lobby.rounds}")
                    Text("\uD83E\uDE99  Min. creditos: ${lobby.minCreditToParticipate}")

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
                        text = "Lista de Espera:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(players) { player ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(player.name, style = MaterialTheme.typography.bodyLarge)
                                    if (player.id == lobby.hostId) {
                                        Badge { Text("HOST") }
                                    }
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
                    OutlinedButton(
                        onClick = onAbandon,
                        modifier = Modifier
                            .weight(1f)
                            .testTag(LOBBYSCREEN_ABANDON_BUTTON),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Abandonar")
                    }

                    Button(
                        onClick = onStartGame,
                        modifier = Modifier
                            .weight(1f)
                            .testTag(LOBBYSCREEN_STARTGAME_BUTTON),
                        // Ativa apenas se houver jogadores suficientes
                        enabled = players.size >= lobby.minUsers
                    ) {
                        Text("Iniciar Jogo")
                    }
                }
            }
        }
    )
}