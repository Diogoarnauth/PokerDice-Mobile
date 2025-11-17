package com.example.chelasmultiplayerpokerdice.playerProfile

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase

@Composable
fun PlayerProfileScreen(viewModel: PlayerProfileViewModel, navigator: PlayerProfileNavigation, user: AuthenticatedUser) {

    LaunchedEffect(user.token) {
        viewModel.loadProfile(user.token)
    }

    val currentState by viewModel.state.collectAsState()

    when (currentState) {
        is PlayerProfileScreenState.Loading -> {
            Text("A carregar perfil...")
        }
        is PlayerProfileScreenState.Success -> {
            PlayerProfileView(
                playerData = (currentState as PlayerProfileScreenState.Success).data,
                goBackTitleScreenFunction = { navigator.goToTitleScreen(user) }
            )
        }
        is PlayerProfileScreenState.Error -> {
            Text(text = (currentState as PlayerProfileScreenState.Error).message)
        }
    }
}


