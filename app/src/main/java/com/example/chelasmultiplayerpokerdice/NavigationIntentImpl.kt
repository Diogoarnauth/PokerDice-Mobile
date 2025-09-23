package com.example.chelasmultiplayerpokerdice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutNavigation
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutScreenActivity
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.ProfileNavigation
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.PlayerProfileActivity
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleNavigation
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreenActivity

@SuppressLint("RestrictedApi")
class NavigationIntentImpl(private val context: Context) : TitleNavigation, AboutNavigation, ProfileNavigation {

    override fun goToTitleScreen() {
        val intent = Intent(context, TitleScreenActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToAboutScreen() {
        val intent = Intent(context, AboutScreenActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToPlayerProfileScreen() {
        val intent = Intent(context, PlayerProfileActivity::class.java)
        context.startActivity(intent)
    }
}