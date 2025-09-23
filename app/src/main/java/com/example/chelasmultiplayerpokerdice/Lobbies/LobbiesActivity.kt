package com.example.chelasmultiplayerpokerdice.Lobbies


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.Lobbies


class LobbiesActivity : ComponentActivity() {

    private val LobbiesScreenService: LobbiesService = LobbiesServiceImpl()
    private val lobbiesNavigation: LobbiesNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lobbies(
                service = LobbiesScreenService,
                navigator = lobbiesNavigation
            )
        }


    }


}