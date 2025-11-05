package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesNavigation
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesScreen
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesViewModel
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesViewModelFactory

class LobbiesActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val lobbiesNavigation: LobbiesNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: LobbiesViewModel =
                viewModel(factory = LobbiesViewModelFactory(app.lobbiesService))

            LobbiesScreen(
                viewModel = viewModel,
                navigator = lobbiesNavigation
            )
        }
    }
}