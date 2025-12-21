package com.example.chelasmultiplayerpokerdice.playerProfile

import android.util.Log
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
            Log.d(" PlayerProfileScreen", "Loaded profile data: ")
            PlayerProfileView(
                playerData = state.data,
                goBackTitleScreenFunction = { navigator.goToTitleScreen(user) },
                inviteCode =  state.inviteCode,
                onDeposit = { credit ->
                    viewModel.deposit(user.token, credit)
                },
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



