package com.example.chelasmultiplayerpokerdice.playerProfileScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl


class PlayerProfileActivity : ComponentActivity() {

    private val profileService: PlayerProfileService = ProfileFakeServiceImpl()
    private val profileNavigation: ProfileNavigation by lazy { NavigationIntentImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Simulação: username vindo do login (depois substituir pelo real)
        val loggedUsername = "renata1234"

        setContent {
            val viewModel: PlayerProfileViewModel =
                viewModel(factory = PlayerProfileViewModelFactory(profileService))

            // carregar dados ao abrir o ecrã
            androidx.compose.runtime.LaunchedEffect(Unit) {
                viewModel.loadProfile(loggedUsername)
            }

            when (val currentState = viewModel.state) {
                is PlayerProfileState.Loading -> {
                    Text("A carregar perfil...")
                }

                is PlayerProfileState.Success -> {
                    PlayerProfileView(
                        playerData = currentState.data,
                        goBackTitleScreenFunction = { profileNavigation.goToTitleScreen() }
                    )
                }

                is PlayerProfileState.Error -> {
                    Text(text = currentState.message)
                }
            }
        }
    }
}

/*

import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel

class PlayerProfileActivity : ComponentActivity() {

    private val profileService: ProfileService = ProfileFakeServiceImpl()
    private val profileNavigation: ProfileNavigation by lazy { NavigationIntentImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Simulação: username vindo do login (depois substituir pelo real)
        val loggedUsername = "renata1234"

        setContent {
            val viewModel: PlayerProfileViewModel =
                viewModel(factory = PlayerProfileViewModelFactory(profileService))

            // carregar dados ao abrir o ecrã
            androidx.compose.runtime.LaunchedEffect(Unit) {
                viewModel.loadProfile(loggedUsername)
            }

            when (val currentState = viewModel.state) {
                is PlayerProfileState.Loading -> {
                    Text("A carregar perfil...")
                }

                is PlayerProfileState.Success -> {
                    PlayerProfileView(
                        playerData = currentState.data,
                        goBackTitleScreenFunction = { profileNavigation.goToTitleScreen() }
                    )
                }

                is PlayerProfileState.Error -> {
                    Text(text = currentState.message)
                }
            }
        }
    }
}

 */
