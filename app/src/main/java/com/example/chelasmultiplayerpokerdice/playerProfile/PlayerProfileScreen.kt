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

    //teste
    LaunchedEffect(user) {
        Log.d("PokerDice", "TITLE SCREEN → user=${user.username}, token=${user.token}")
    }
    //teste
    LaunchedEffect(user) {
        Log.d("lista de tokens e users", "TITLE SCREEN → tokens=${FakeDatabase.tokens}, users=${FakeDatabase.users}")
    }

    LaunchedEffect(user.token) {
        viewModel.loadProfile(user.token)
    }

    val currentState by viewModel.state.collectAsState()

    LaunchedEffect(user) {
        Log.d("currentState", "TITLE SCREEN → tou farto= ${(currentState)}")
    }

    when (currentState) {
        is PlayerProfileScreenState.Loading -> {
            Text("A carregar perfil...")
        }
        is PlayerProfileScreenState.Success -> {
            LaunchedEffect(user) {
                Log.d("teste", "TITLE SCREEN → tou farto= ${(currentState as PlayerProfileScreenState.Success).data}")
            }
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


