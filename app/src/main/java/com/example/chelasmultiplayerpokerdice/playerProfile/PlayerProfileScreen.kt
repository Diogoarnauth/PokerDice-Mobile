package com.example.chelasmultiplayerpokerdice.playerProfile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

@Composable
fun PlayerProfileScreen(viewModel: PlayerProfileViewModel, navigator: PlayerProfileNavigation, user: AuthenticatedUser) {
    when (val currentState = viewModel.state) {
        is PlayerProfileScreenState.Loading -> {
            Text("A carregar perfil...")
        }

        is PlayerProfileScreenState.Success -> {
            PlayerProfileView(
                playerData = currentState.data,
                goBackTitleScreenFunction = { navigator.goToTitleScreen(user) }
            )
        }

        is PlayerProfileScreenState.Error -> {
            Text(text = currentState.message)
        }
    }
}


