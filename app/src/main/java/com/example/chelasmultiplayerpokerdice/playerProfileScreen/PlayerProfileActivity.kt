package com.example.chelasmultiplayerpokerdice.playerProfileScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl
import com.example.chelasmultiplayerpokerdice.aboutScreen.ProfileNavigation
import com.example.chimp.home.ProfileService
import com.example.chimp.home.ProfileServiceImpl
import playerProfile


class PlayerProfileActivity : ComponentActivity() {

    private val playerProfileService: ProfileService = ProfileServiceImpl()
    private val playerNavigation: ProfileNavigation by lazy {
        NavigationIntentImpl(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            playerProfile(
                service = playerProfileService,
                navigator = playerNavigation
            )
        }


    }


}