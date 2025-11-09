package com.example.chelasmultiplayerpokerdice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreation
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationNavigation
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationViewModel
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationViewModelFactory

class LobbyCreationActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val lobbyCreationNavigation: LobbyCreationNavigation by lazy {
        NavigationIntentImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra(AUTHENTICATED_USER_EXTRA) as? AuthenticatedUser
        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()

        setContent {
            val viewModel: LobbyCreationViewModel =
                viewModel(factory = LobbyCreationViewModelFactory(app.lobbyCreationService))

            LobbyCreation(
                viewModel = viewModel,
                navigator = lobbyCreationNavigation,
                user = user
            )
        }
    }
}