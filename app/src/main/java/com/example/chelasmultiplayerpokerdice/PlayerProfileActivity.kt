package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileNavigation
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileScreen
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileViewModel
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileViewModelFactory

class PlayerProfileActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val playerProfileNavigation: PlayerProfileNavigation by lazy { NavigationIntentImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: PlayerProfileViewModel =
                viewModel(factory = PlayerProfileViewModelFactory(app.playerProfileService))

            PlayerProfileScreen(
                viewModel = viewModel,
                navigator = playerProfileNavigation
            )
        }
    }
}
