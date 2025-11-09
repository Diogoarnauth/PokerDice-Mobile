package com.example.chelasmultiplayerpokerdice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
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

        val user = intent.getSerializableExtra(AUTHENTICATED_USER_EXTRA) as? AuthenticatedUser
        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        enableEdgeToEdge()

        setContent {
            val viewModel: LobbiesViewModel =
                viewModel(factory = LobbiesViewModelFactory(app.lobbiesService))

            LobbiesScreen(
                viewModel = viewModel,
                navigator = lobbiesNavigation,
                user = user
            )
        }
    }
}