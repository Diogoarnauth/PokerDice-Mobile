package com.example.chimp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

const val PLAYERPROFILE_BACK_TITLESCREEN = "Player Profile back to title screen"
const val PLAYERPROFILE_VIEW_TAG = "Player Profile View"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProfileView(
    playerUsername: String,
    playerName: String,
    playerAge: Int,
    goBackTitleScreenFunction: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(PLAYERPROFILE_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "👤 Perfil do Jogador",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = goBackTitleScreenFunction,
                        modifier = Modifier.testTag(PLAYERPROFILE_BACK_TITLESCREEN)
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Go back to title screen")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Informações do Jogador",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "🆔 Username: $playerUsername",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "👤 Nome: $playerName",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "🎂 Idade: $playerAge",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayerProfileViewPreview() {
    PlayerProfileView(
        playerUsername = "renata1234",
        playerName = "Renata Castanheira",
        playerAge = 19,
        goBackTitleScreenFunction = { }
    )
}
