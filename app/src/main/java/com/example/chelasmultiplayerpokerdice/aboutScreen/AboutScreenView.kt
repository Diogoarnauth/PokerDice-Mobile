package com.example.chelasmultiplayerpokerdice.aboutScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

const val ABOUTSCREEN_VIEW_TAG = "About View"
const val ABOUTSCREEN_EMAIL_BUTTON = "Email button on About screen"
const val ABOUTSCREEN_WEB_LINK = "Web link on About screen"
const val TITLESCREEN_BUTTON = "Button to return to Title Screen"

data class Author(val name: String, val number: Int, val email: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreenView(
    members: List<Author>,
    gameplayUrl: String,
    titleScreenFunction: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(ABOUTSCREEN_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ℹ️ Sobre o Jogo",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = titleScreenFunction,
                        modifier = Modifier.testTag(TITLESCREEN_BUTTON)
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Poker Dice é um jogo multijogador competitivo que combina elementos de jogos de dados com a lógica clássica do poker. Cada jogador lança um conjunto de dados com o objetivo de formar a melhor combinação possível.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "🎲 Características do Jogo",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "🎮 Número mínimo de jogadores: 2\n" +
                                "🧑‍🤝‍🧑 Número máximo de jogadores: 4\n" +
                                "⏱️ Duração média: 5 a 10 minutos\n" +
                                "🧠 Objetivo: Fazer a melhor jogada de poker com cinco dados.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Ver descrição completa",
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(gameplayUrl))
                                context.startActivity(intent)
                            }
                            .testTag(ABOUTSCREEN_WEB_LINK),
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        text = "Membros do Grupo",
                        style = MaterialTheme.typography.titleMedium
                    )

                    members.forEach { (nome, numero) ->
                        Text(
                            text = "- $nome ($numero)",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val emails = members.joinToString(",") { it.email }
                            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:$emails")
                            }
                            context.startActivity(emailIntent)
                        },
                        modifier = Modifier.testTag(ABOUTSCREEN_EMAIL_BUTTON)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = "Email")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Contactar todos por email")
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AboutScreenViewPreview() {
    AboutScreenView(
        members = listOf(
            Author("Diogo Arnauth", 51634, "dioarnauth@gmail.com"),
            Author("Renata Castanheira", 51830, "renataCatanheira@gmail.com"), // catanheira ou caStanheira?
            Author("Humberto Carvalho", 50500, "betocp@sapo.pt")
        ),
        gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice",
        titleScreenFunction = {}
    )
}