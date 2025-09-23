package com.example.chelasmultiplayerpokerdice.titleScreen
import androidx.compose.runtime.Composable

@Composable
fun TitleScreen(service: TitleScreenService, navigator: TitleNavigation) {
    TitleScreenView(
        creators = service.getCreators(),
        startMatchFunction = { /* TODO: lógica para iniciar jogo */ },
        profileFunction = { navigator.goToPlayerProfileScreen() },
        aboutFunction = { navigator.goToAboutScreen() }
    )
}