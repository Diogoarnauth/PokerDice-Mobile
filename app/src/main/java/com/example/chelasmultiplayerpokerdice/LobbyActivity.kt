package com.example.chelasmultiplayerpokerdice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.lobby.LobbyNavigation
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreen
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreenViewModel
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreenViewModelFactory

class LobbyActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val lobbyNavigation: LobbyNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra(AUTHENTICATED_USER_EXTRA) as? AuthenticatedUser

        val lobbyId = intent.getIntExtra(LOBBY_ID_EXTRA, -1)

        if (user == null || lobbyId == -1) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            val viewModel: LobbyScreenViewModel =
                viewModel(factory = LobbyScreenViewModelFactory(app.lobbyService))
            LobbyScreen(
                viewModel = viewModel,
                navigator = lobbyNavigation,
                user = user,
                lobbyId = lobbyId
            )
        }
    }
}