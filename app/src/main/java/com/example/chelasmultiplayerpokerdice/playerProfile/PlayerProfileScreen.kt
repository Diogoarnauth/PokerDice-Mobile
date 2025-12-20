package com.example.chelasmultiplayerpokerdice.playerProfile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
@Composable
fun PlayerProfileScreen(
    viewModel: PlayerProfileViewModel,
    navigator: PlayerProfileNavigation,
    user: AuthenticatedUser
) {

    LaunchedEffect(user.token) {
        viewModel.loadProfile(user.token)
    }

    val currentState by viewModel.state.collectAsState()

    when (val state = currentState) {
        is PlayerProfileScreenState.Loading -> {
            Text("A carregar perfil...")
        }
        is PlayerProfileScreenState.Success -> {
            PlayerProfileView(
                playerData = state.data,
                inviteCode = state.inviteCode,
                goBackTitleScreenFunction = { navigator.goToTitleScreen(user) },
                onGetInviteCode = {
                    viewModel.generateInvite(user.token)
                }
            )
        }
        is PlayerProfileScreenState.Error -> {
            Text(text = state.message)
        }
    }
}

