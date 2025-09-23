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

// ---------- Constantes de Testes ----------
const val LOBBIES_VIEW_TAG = "Lobbies View"
const val LOBBIES_BACK_TITLESCREEN = "Back to Title Screen"
const val LOBBIES_CREATE_BUTTON = "Create Lobby Button"
const val LOBBY_CARD_TAG = "Lobby Card"

// ---------- Modelo ----------

data class Player(val id: Int, val name: String)
data class Lobby(
    val id:Int,
    val name: String,
    val owner: String,
    val description : String,
    val rounds: Int,
    val isPrivate: Boolean,
    val password: String?,
    val maxPlayers: Int,
    val players: List<Player>
)


// ---------- UI ----------
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
                // Caso não haja lobbies
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
                // Lista de lobbies
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
                                    text = "👑 Dono: ${lobby.owner}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = if (lobby.isPrivate) "🔒 Privado" else "🌍 Público",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Jogadores: ${lobby.players.size} / ${lobby.maxPlayers}",
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

// ---------- Preview ----------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LobbiesViewPreview() {
    val sampleLobbies = listOf(
        Lobby(0,"Poker Stars", "Renata", "jogo para aprender", 5,false, null,9,  emptyList<Player>()),
        Lobby(1,"Lucky Dice", "Diogo", "jogo para intermedios", 6,true, "passSecreta",4, emptyList<Player>()),
        Lobby(2,"Chelas Crew", "Humberto", "jogo para pros",7, false, null,1,  emptyList<Player>()),
    )

    LobbiesView(
        lobbies = sampleLobbies,
        goBackTitleScreenFunction = { },
        createLobbyFunction = { },
        selectLobbyFunction = { }
    )
}
