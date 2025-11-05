package com.example.chelasmultiplayerpokerdice
import android.content.Intent
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesNavigation
import com.example.chelasmultiplayerpokerdice.about.AboutNavigation
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileNavigation
import android.annotation.SuppressLint
import android.content.Context
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationNavigation
import com.example.chelasmultiplayerpokerdice.lobby.LobbyNavigation
import com.example.chelasmultiplayerpokerdice.title.TitleNavigation

@SuppressLint("RestrictedApi")
    class NavigationIntentImpl(private val context: Context) : TitleNavigation, AboutNavigation, PlayerProfileNavigation, LobbiesNavigation, LobbyNavigation, LobbyCreationNavigation {
        override fun goToLobbyDetailsScreen(lobbyId: Int) {
            val intent = Intent(context, LobbyActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }

        override fun goToLobbyCreationScreen() {
            val intent = Intent(context, LobbyCreationActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)        }

        override fun goToTitleScreen() {
            val intent = Intent(context, TitleActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }

        override fun goToAboutScreen() {
            val intent = Intent(context, AboutActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }

        override fun goToPlayerProfileScreen() {
            val intent = Intent(context, PlayerProfileActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }

        override fun goToGameScreen() {
            TODO("Not yet implemented")
        }

        override fun goToLobbiesScreen() {
            val intent = Intent(context, LobbiesActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }
    }