package com.example.chelasmultiplayerpokerdice.aboutScreen
import androidx.compose.runtime.Composable

@Composable
fun AboutScreen(service: AboutService, navigator: AboutNavigation) {
    AboutScreenView(
        members = service.getMembers(),
        gameplayUrl = service.getGamePlayUrl(),
        titleScreenFunction = {navigator.goToTitleScreen()}
    )
}