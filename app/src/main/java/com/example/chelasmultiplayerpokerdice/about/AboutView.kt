package com.example.chelasmultiplayerpokerdice.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGold
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText

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
                        text = "About Chelas Poker Dice",
                        style = MaterialTheme.typography.titleLarge,
                        color = pokerText
                    )
                },
                actions = {
                    IconButton(
                        onClick = titleScreenFunction,
                        modifier = Modifier.testTag(TITLESCREEN_BUTTON)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
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
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(pokerGreen, pokerGreenDark))
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .background(Color(0xCC061F17))
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Poker Dice é um jogo multijogador competitivo que combina elementos de jogos de dados com a lógica clássica do poker. Cada jogador lança um conjunto de dados com o objetivo de formar a melhor combinação possível.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = pokerText
                )

                Text(
                    text = "🎲 Características do Jogo",
                    style = MaterialTheme.typography.titleMedium,
                    color = pokerGold
                )

                Text(
                    text = "🎮 Número mínimo de jogadores: 2\n" +
                            "🧑‍🤝‍🧑 Número máximo de jogadores: 4\n" +
                            "⏱️ Duração média: 5 a 10 minutos\n" +
                            "🧠 Objetivo: Fazer a melhor jogada de poker com cinco dados.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    color = pokerText
                )

                Text(
                    text = "Ver descrição completa",
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(gameplayUrl))
                            context.startActivity(intent)
                        }
                        .testTag(ABOUTSCREEN_WEB_LINK),
                    color = pokerGreen,
                    textDecoration = TextDecoration.Underline
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = pokerGold.copy(alpha = 0.5f)
                )

                Text(
                    text = "Membros do Grupo",
                    style = MaterialTheme.typography.titleMedium,
                    color = pokerText
                )

                members.forEach { (nome, numero, _) ->
                    Text(
                        text = "- $nome ($numero)",
                        style = MaterialTheme.typography.bodySmall,
                        color = pokerText
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
                    modifier = Modifier.testTag(ABOUTSCREEN_EMAIL_BUTTON),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    )
                ) {
                    Icon(Icons.Default.Email, contentDescription = "Email")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Contactar todos por email")
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AboutScreenViewPreview() {
    AboutScreenView(
        members = listOf(
            Author("Diogo Arnauth", 51634, "dioarnauth@gmail.com"),
            Author("Renata Castanheira", 51830, "renataCatanheira@gmail.com"),
            Author("Humberto Carvalho", 50500, "betocp@sapo.pt")
        ),
        gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice",
        titleScreenFunction = {}
    )
}
