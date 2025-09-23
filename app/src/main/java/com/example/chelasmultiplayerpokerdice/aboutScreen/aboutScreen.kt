package com.example.chelasmultiplayerpokerdice.lobbyScreen
import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutNavigation
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutScreenView
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutService

@Composable
fun AboutScreen(service: AboutService, navigator: AboutNavigation) {
    AboutScreenView(
        members = service.getMembers(),
        gameplayUrl = service.getGamePlayUrl(),
        titleScreenFunction = {navigator.goToTitleScreen()}
    )
}