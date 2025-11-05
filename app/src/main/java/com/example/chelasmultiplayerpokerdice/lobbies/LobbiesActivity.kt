package com.example.chelasmultiplayerpokerdice.lobbies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl
import androidx.compose.material3.Text

class LobbiesActivity : ComponentActivity() {

    private val service: LobbiesService = LobbiesFakeServiceImpl()
    private val navigator: LobbiesNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: LobbiesViewModel =
                viewModel(factory = LobbiesViewModelFactory(service))

            when (val currentState = viewModel.state) {
                is LobbiesScreenState.Loading -> {
                    Text("A carregar lobbies...")
                }

                is LobbiesScreenState.Success -> {
                    LobbiesView(
                        lobbies = currentState.lobbies,
                        goBackTitleScreenFunction = { navigator.goToTitleScreen() },
                        createLobbyFunction = { navigator.goToLobbyCreationScreen() },
                        selectLobbyFunction = { lobby ->
                            navigator.goToLobbyDetailsScreen(lobby.id)
                        }
                    )
                }

                is LobbiesScreenState.Error -> {
                    Text("Erro: ${currentState.error.message}")
                }
            }
        }
    }
}
