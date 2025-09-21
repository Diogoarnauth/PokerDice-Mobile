package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.ui.theme.ChelasMultiPlayerPokerDiceTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasMultiPlayerPokerDiceTheme {
               // MyApp()
            }
        }
    }
}



/* Tela sobre
        composable("about_screen") {
            AboutScreenView(
                members = listOf(
                    Author("Diogo Arnauth", 51634, "dioarnauth@gmail.com"),
                    Author("Renata Castanheira", 51830, "renataCatanheira@gmail.com"), // catanheira ou caStanheira?
                    Author("Humberto Carvalho", 50500, "betocp@sapo.pt")
                ),
                gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice",
                titleScreenFunction = {
                    navController.navigate("title_screen") {
                        popUpTo("title_screen") { inclusive = true }
                    }
                }
            )
        }

    }
}
*/



