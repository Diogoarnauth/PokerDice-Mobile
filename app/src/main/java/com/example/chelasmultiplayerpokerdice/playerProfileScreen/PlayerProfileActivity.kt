package com.example.chelasmultiplayerpokerdice.playerProfileScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl


class PlayerProfileActivity : ComponentActivity() {

    private val playerProfileService: ProfileService = ProfileServiceImpl()
    private val playerNavigation: ProfileNavigation by lazy {
        NavigationIntentImpl(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayerProfile(
                service = playerProfileService,
                navigator = playerNavigation
            )
        }


    }


}