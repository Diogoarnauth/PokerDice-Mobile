package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.domain.*

const val LOBBIES_VIEW_TAG = "Lobbies View"
const val LOBBIES_BACK_TITLESCREEN = "Back to Title Screen"
const val LOBBIES_CREATE_BUTTON = "Create Lobby Button"
const val LOBBY_CARD_TAG = "Lobby Card"



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbiesView(
    lobbies: List<Lobby>,
    goBackTitleScreenFunction: () -> Unit,
    createLobbyFunction: () -> Unit,
    selectLobbyFunction: (Lobby) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LOBBIES_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🎲 Lobbies Disponíveis",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = goBackTitleScreenFunction,
                        modifier = Modifier.testTag(LOBBIES_BACK_TITLESCREEN)
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Voltar ao Title Screen")
                    }
                },
                actions = {
                    IconButton(
                        onClick = createLobbyFunction,
                        modifier = Modifier.testTag(LOBBIES_CREATE_BUTTON)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Criar Lobby")
                    }
                }
            )
        },
        content = { padding ->
            if (lobbies.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum lobby disponível 👀",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(lobbies) { lobby ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag(LOBBY_CARD_TAG),
                            onClick = { selectLobbyFunction(lobby) }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = lobby.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "👑 Dono: ${lobby.hostId}",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    text = "Jogadores: ${lobby.users.size} / ${lobby.maxUsers}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LobbiesViewPreview() {
    val sampleLobbies = listOf(
        Lobby(
            id = 0,
            name = "Poker Stars",
            description = "jogo para aprender",
            hostId = 1,
            minUsers = 2,
            maxUsers = 10,
            rounds = 5,
            minCreditToParticipate = 0,
            playersCount = 9,
            users = emptyList()
        ),
        Lobby(
            id = 1,
            name = "Lucky Dice",
            description = "jogo para intermedios",
            hostId = 2,
            minUsers = 2,
            maxUsers = 10,
            rounds = 6,
            minCreditToParticipate = 0,
            playersCount = 4,
            users = emptyList()
        ),
        Lobby(
            id = 2,
            name = "Chelas Crew",
            description = "jogo para pros",
            hostId = 3,
            minUsers = 2,
            maxUsers = 10,
            rounds = 7,
            minCreditToParticipate = 0,
            playersCount = 1,
            users = emptyList()
        )
    )

    LobbiesView(
        lobbies = sampleLobbies,
        goBackTitleScreenFunction = { },
        createLobbyFunction = { },
        selectLobbyFunction = { }
    )
}
