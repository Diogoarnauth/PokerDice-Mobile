package com.example.chelasmultiplayerpokerdice.aboutScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.home.TitleScreenView

const val ABOUTSCREEN_VIEW_TAG = "About View"
const val ABOUTSCREEN_EMAIL_BUTTON = "Email button on About screen"
const val ABOUTSCREEN_WEB_LINK = "Web link on About screen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreenView(
    members: List<Pair<String, String>>, // nome e número
    emailList: List<String>,
    gameplayUrl: String
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(ABOUTSCREEN_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = { Text("About Us/Game") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {

            // Descrição geral
            Text(
                text = "Poker Dice é um jogo multijogador competitivo que combina elementos de jogos de dados com a lógica clássica do poker. Cada jogador lança um conjunto de dados com o objetivo de formar a melhor combinação possível, tal como no poker tradicional (pares, trincas, full house, etc.).",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            Text(
                text = "🎲 Características do Jogo\n",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "🎮 Número mínimo de jogadores: 2\n\n" +
                        "🧑‍🤝‍🧑 Número máximo de jogadores: 4\n\n" +
                        "⏱️ Duração média de cada partida: 5 a 10 minutos\n\n" +
                        "🧠 Objetivo: Obter a melhor jogada de poker com cinco dados lançados em cada ronda.",
                style = MaterialTheme.typography.bodyMedium
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

            Spacer(Modifier.height(16.dp))

            // Membros
            Text("Membros do Grupo:", style = MaterialTheme.typography.titleMedium)
            members.forEach { (nome, numero) ->
                Text("- $nome ($numero)")
            }

            Spacer(Modifier.height(16.dp))

            // Botão de Email
            Button(
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:" + emailList.joinToString(","))
                    }
                    context.startActivity(emailIntent)
                },
                modifier = Modifier.testTag(ABOUTSCREEN_EMAIL_BUTTON)
            ) {
                Text("Contactar todos por email")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenViews() {
    AboutScreenView(
        members = listOf(
            Pair("Diogo Arnauth", "51634"),
            Pair("Renata Castanheira", "51830"),
            Pair("Humberto Carvalho", "xxxxx"),
            ),
        emailList = listOf("dioarnauth@gmail.com", "renataCatanheira@gmail.com", "humbertoCarvalho@gmail.com"),
        gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice"

    )
}
