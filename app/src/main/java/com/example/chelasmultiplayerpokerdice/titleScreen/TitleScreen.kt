package com.example.chelasmultiplayerpokerdice.titleScreen
import androidx.compose.runtime.Composable

@Composable
fun TitleScreen(service: TitleScreenService, navigator: TitleScreenNavigation) {
    TitleScreenView(
        creators = service.getCreators(),
        startMatchFunction = { navigator.goToLobbiesScreen() },
        profileFunction = { navigator.goToPlayerProfileScreen() },
        aboutFunction = { navigator.goToAboutScreen() }
    )
}