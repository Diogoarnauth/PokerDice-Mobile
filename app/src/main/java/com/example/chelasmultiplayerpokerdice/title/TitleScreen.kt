package com.example.chelasmultiplayerpokerdice.title

import androidx.compose.runtime.Composable

@Composable
fun TitleScreen(viewModel: TitleViewModel, navigator: TitleNavigation) {

    when (val currentState = viewModel.state) {
        is TitleScreenState.Success -> {
            TitleScreenView(
                creators = currentState.creators,
                startMatchFunction = { navigator.goToLobbiesScreen() },
                profileFunction = { navigator.goToPlayerProfileScreen() },
                aboutFunction = { navigator.goToAboutScreen() }
            )
        }
    }
}
