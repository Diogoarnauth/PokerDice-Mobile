package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.chelasmultiplayerpokerdice.ui.theme.ChelasMultiplayerPokerDiceTheme

class LobbyCreationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChelasMultiplayerPokerDiceTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    LobbyCreationScreen(
                        onBack = { finish() },
                        onCreateLobby = { name, desc, players, rounds ->
                            // TODO: Implement lobby creation and navigation
                        }
                    )
                }
            }
        }
    }
}
