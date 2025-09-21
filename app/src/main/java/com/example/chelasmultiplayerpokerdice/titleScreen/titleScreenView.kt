package com.example.chimp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
                title = { Text("Chelas Multi-player Poker Dice") },
                actions = {
                    IconButton(
                        onClick = startMatchFunction,
                        modifier = Modifier.testTag(TITLESCREEN_STARTMATCH_BUTTON)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Start Match")
                    }
                    IconButton(
                        onClick = profileFunction,
                        modifier = Modifier.testTag(TITLESCREEN_PROFILE_BUTTON)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(
                         onClick = { aboutFunction() },
                        modifier = Modifier.testTag(TITLESCREEN_ABOUT_BUTTON)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = "About")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Parte de cima: mensagem de boas-vindas
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "welcome",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Parte de baixo: criadores
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.testTag(TITLESCREEN_MADE_BY_TEXT),
                    text = "creators",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
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
}

@Composable
fun GenericTopAppBar(title: String, actions: @Composable () -> Unit) {
    TODO("Not yet Implemented")
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TitleScreenView() {
    TitleScreenView(
        creators = listOf(
            "Diogo Arnauth",
            "Humberto Carvalho",
            "Renata Castanheira",
        ),
        startMatchFunction = { },
        profileFunction = { },
        aboutFunction = { }
    )
}