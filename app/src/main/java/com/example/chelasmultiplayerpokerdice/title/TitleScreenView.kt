package com.example.chelasmultiplayerpokerdice.title

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGold
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText

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
                        text = "Chelas Poker Dice",
                        color = pokerText,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = profileFunction,
                        modifier = Modifier.testTag(TITLESCREEN_PROFILE_BUTTON)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = pokerText
                        )
                    }
                    IconButton(
                        onClick = aboutFunction,
                        modifier = Modifier.testTag(TITLESCREEN_ABOUT_BUTTON)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            tint = pokerText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pokerRed,
                    titleContentColor = pokerGold
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(pokerGreen, pokerGreenDark)
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Centro: título e botão
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to",
                    style = MaterialTheme.typography.titleMedium,
                    color = pokerText
                )
                Text(
                    text = "Chelas Poker Dice",
                    style = MaterialTheme.typography.headlineMedium,
                    color = pokerGold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )

                // Botão “Start Match”
                Button(
                    onClick = startMatchFunction,
                    modifier = Modifier
                        .testTag(TITLESCREEN_STARTMATCH_BUTTON)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Find Lobbies"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Find Lobbies")
                }
            }

            // Rodapé: “creators”
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.testTag(TITLESCREEN_MADE_BY_TEXT),
                    text = "Created by",
                    style = MaterialTheme.typography.bodySmall,
                    color = pokerText.copy(alpha = 0.7f)
                )
                creators.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = pokerText
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TitleScreenViewPreview() {
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