package com.example.chelasmultiplayerpokerdice.title

import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

@Composable
fun TitleScreen(viewModel: TitleViewModel, navigator: TitleNavigation, user: AuthenticatedUser) {

    when (val currentState = viewModel.state) {
        is TitleScreenState.Success -> {
            TitleScreenView(
                creators = currentState.creators,
                startMatchFunction = { navigator.goToLobbiesScreen(user) },
                profileFunction = { navigator.goToPlayerProfileScreen(user) },
                aboutFunction = { navigator.goToAboutScreen(user) },

                )
        }
    }
}
