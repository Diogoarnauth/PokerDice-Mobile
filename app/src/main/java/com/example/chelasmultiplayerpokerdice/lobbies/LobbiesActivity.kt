package com.example.chelasmultiplayerpokerdice.lobbies


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl


class LobbiesActivity : ComponentActivity() {

    private val lobbiesScreenService: LobbiesService = LobbiesFakeServiceImpl()
    private val lobbiesNavigation: LobbiesNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lobbies(
                service = lobbiesScreenService,
                navigator = lobbiesNavigation
            )
        }


    }


}