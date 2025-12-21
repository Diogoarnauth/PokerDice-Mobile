package com.example.chelasmultiplayerpokerdice.lobby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGold
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText

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
    currentUserId: Int,
    goBackTitleScreenFunction: () -> Unit,
    onAbandon: () -> Unit,
    onJoinLobby: () -> Unit,
    onStartGame: () -> Unit,
) {
    val isHost = lobby.hostId == currentUserId
    val isAlreadyInLobby = players.any { it.id == currentUserId }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LOBBYSCREEN_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lobby: ${lobby.name}",
                        style = MaterialTheme.typography.titleLarge,
                        color = pokerText
                    )
                },
                actions = {
                    IconButton(onClick = goBackTitleScreenFunction) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Back to Menu",
                            tint = pokerText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pokerRed,
                    titleContentColor = pokerText
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(pokerGreen, pokerGreenDark))
                )
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Info do lobby
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(LOBBYSCREEN_LOBBY_INFO),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = lobby.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = pokerGreenDark
                )
                Text(
                    text = "Players: ${players.size}/${lobby.maxUsers}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = pokerGreenDark
                )
                Text(
                    text = "Rounds: ${lobby.rounds}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = pokerGreenDark
                )
                Text(
                    text = "Min. credits: ${lobby.minCreditToParticipate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = pokerGreenDark
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de jogadores
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag(LOBBYSCREEN_PLAYERS_LIST)
            ) {
                Text(
                    text = "Waiting Room",
                    style = MaterialTheme.typography.titleMedium,
                    color = pokerGold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(players) { player ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xCC061F17)
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    player.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = pokerText
                                )
                                if (player.id == lobby.hostId) {
                                    Badge(
                                        containerColor = pokerGold
                                    ) {
                                        Text(
                                            "HOST",
                                            color = pokerRed,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botões em baixo
            if (isAlreadyInLobby) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onAbandon,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Leave Lobby")
                    }

                    if (isHost) {
                        Button(
                            onClick = onStartGame,
                            modifier = Modifier.weight(1f),
                            enabled = players.size >= lobby.minUsers,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = pokerGold,
                                contentColor = pokerRed
                            )
                        ) {
                            Text("Start Game")
                        }
                    }
                }
            } else {
                Button(
                    onClick = onJoinLobby,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    )
                ) {
                    Text("Join Lobby")
                }
            }
        }
    }
}
