package com.example.chelasmultiplayerpokerdice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent

@SuppressLint("RestrictedApi")
class NavigationIntentImpl(private val context: Context) :
    TitleScreenNavigation {

    override fun goToTitleScreen() {
        val intent = Intent(context, TitleScreenActivityActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }
}