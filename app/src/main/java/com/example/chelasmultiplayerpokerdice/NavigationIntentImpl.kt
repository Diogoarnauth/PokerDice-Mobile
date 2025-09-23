package com.example.chelasmultiplayerpokerdice
import android.content.Intent
import com.example.chelasmultiplayerpokerdice.Lobbies.LobbiesActivity
import com.example.chelasmultiplayerpokerdice.Lobbies.LobbiesNavigation
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutNavigation
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutScreenActivity
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.ProfileNavigation
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.PlayerProfileActivity

import android.annotation.SuppressLint
import android.content.Context
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleNavigation
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreenActivity

    @SuppressLint("RestrictedApi")
    class NavigationIntentImpl(private val context: Context) : TitleNavigation, AboutNavigation, ProfileNavigation, LobbiesNavigation {
        override fun goToLobbyDetailsScreen(lobbyId: Int) {
            TODO("Not yet implemented")
        }

        override fun goToLobbyCreationScreen() {
            TODO("Not yet implemented")
        }

        override fun goToTitleScreen() {
            val intent = Intent(context, TitleScreenActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }

        override fun goToAboutScreen() {
            val intent = Intent(context, AboutScreenActivity::class.java).apply {
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

        fun goToLobbiesScreen() {
            val intent = Intent(context, LobbiesActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }
    }