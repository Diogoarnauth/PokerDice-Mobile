package com.example.chimp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

const val TITLESCREEN_VIEW_TAG = "Title View"
const val TITLESCREEN_STARTMATCH_BUTTON = "Start match button on title screen"
const val TITLESCREEN_PROFILE_BUTTON = "Profile button on title screen"
const val TITLESCREEN_ABOUT_BUTTON = "About button on title screen"
const val TITLESCREEN_MADE_BY_TEXT = "made by text on Title screen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleScreenView(
    creators: List<String>,
    startMatchFunction: () -> Unit,
    profileFunction: () -> Unit,
    aboutFunction: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TITLESCREEN_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🎲 Chelas Poker Dice",
                        style = MaterialTheme.typography.titleLarge
                    )
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
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Parte de boas-vindas e botões
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bem-vindo ao jogo!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Prepara-te para desafiar os teus amigos\nnum jogo de dados ao estilo Poker.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    // Botões principais
                    Column(
                        modifier = Modifier.padding(top = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = startMatchFunction,
                            modifier = Modifier.testTag(TITLESCREEN_STARTMATCH_BUTTON)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Start Match")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Iniciar Jogo")
                        }

                        Button(
                            onClick = profileFunction,
                            modifier = Modifier.testTag(TITLESCREEN_PROFILE_BUTTON)
                        ) {
                            Icon(Icons.Default.Person, contentDescription = "Profile")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Perfil")
                        }

                        OutlinedButton(
                            onClick = aboutFunction,
                            modifier = Modifier.testTag(TITLESCREEN_ABOUT_BUTTON)
                        ) {
                            Icon(Icons.Default.Info, contentDescription = "About")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sobre o Jogo")
                        }
                    }
                }

                // Rodapé com criadores
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Criado por:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.testTag(TITLESCREEN_MADE_BY_TEXT)
                    )
                    creators.forEach {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TitleScreenViewPreview() {
    TitleScreenView(
        creators = listOf(
            "Diogo Arnauth",
            "Humberto Carvalho",
            "Renata Castanheira"
        ),
        startMatchFunction = {},
        profileFunction = {},
        aboutFunction = {}
    )
}