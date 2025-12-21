package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.LobbyInfo
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText

const val LOBBIES_VIEW_TAG = "Lobbies View"
const val LOBBIES_BACK_TITLESCREEN = "Back to Title Screen"
const val LOBBIES_CREATE_BUTTON = "Create Lobby Button"
const val LOBBY_CARD_TAG = "Lobby Card"




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbiesView(
    lobbies: List<LobbyInfo>,
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
                        text = "Available Lobbies",
                        style = MaterialTheme.typography.titleLarge,
                        color = pokerText
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = goBackTitleScreenFunction,
                        modifier = Modifier.testTag(LOBBIES_BACK_TITLESCREEN)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Back to Title Screen",
                            tint = pokerText
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = createLobbyFunction,
                        modifier = Modifier.testTag(LOBBIES_CREATE_BUTTON)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Create Lobby",
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
        if (lobbies.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(pokerGreen, pokerGreenDark)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No lobbies available 👀\nCreate one to start playing!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = pokerText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(pokerGreen, pokerGreenDark)
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(lobbies) { lobbyInfo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(LOBBY_CARD_TAG),
                        onClick = { selectLobbyFunction(lobbyInfo.lobby) }
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color(0xCC061F17))
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = lobbyInfo.lobby.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = pokerText
                            )
                            Text(
                                text = "Host: ${lobbyInfo.lobby.hostId}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = pokerText
                            )
                            Text(
                                text = "Players: ${lobbyInfo.playerCount} / ${lobbyInfo.lobby.maxUsers}",
                                style = MaterialTheme.typography.bodySmall,
                                color = pokerText.copy(alpha = 0.8f)
                            )
                            Text(
                                text = lobbyInfo.lobby.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = pokerText.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LobbiesViewPreview() {
    val lobby1 = Lobby(
        id = 0,
        name = "Poker Stars",
        description = "jogo para aprender",
        hostId = 1,
        minUsers = 2,
        maxUsers = 10,
        rounds = 5,
        minCreditToParticipate = 0
    )
    val lobby2 = Lobby(
        id = 1,
        name = "Lucky Dice",
        description = "jogo para intermedios",
        hostId = 2,
        minUsers = 2,
        maxUsers = 10,
        rounds = 6,
        minCreditToParticipate = 0
    )
    val lobby3 = Lobby(
        id = 2,
        name = "Chelas Crew",
        description = "jogo para pros",
        hostId = 3,
        minUsers = 2,
        maxUsers = 10,
        rounds = 7,
        minCreditToParticipate = 0
    )

    val sampleLobbyInfos = listOf(
        LobbyInfo(lobby = lobby1, playerCount = 5),
        LobbyInfo(lobby = lobby2, playerCount = 2),
        LobbyInfo(lobby = lobby3, playerCount = 8)
    )


    LobbiesView(
        lobbies = sampleLobbyInfos,
        goBackTitleScreenFunction = { },
        createLobbyFunction = { },
        selectLobbyFunction = { }
    )
}
