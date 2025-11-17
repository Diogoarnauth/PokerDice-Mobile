package com.example.chelasmultiplayerpokerdice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.game.GameNavigation
import com.example.chelasmultiplayerpokerdice.game.GameScreen
import com.example.chelasmultiplayerpokerdice.game.GameViewModel
import com.example.chelasmultiplayerpokerdice.game.GameViewModelFactory

class GameActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }
    private val navigator: GameNavigation by lazy { NavigationIntentImpl(this) }

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
            val viewModel: GameViewModel =
                viewModel(factory = GameViewModelFactory(app.gameService))

            GameScreen(
                viewModel = viewModel,
                navigator = navigator,
                user = user,
                lobbyId = lobbyId
            )
        }
    }
}