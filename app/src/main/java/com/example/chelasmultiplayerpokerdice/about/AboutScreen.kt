package com.example.chelasmultiplayerpokerdice.about
import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

@Composable
fun AboutScreen(service: AboutService, navigator: AboutNavigation, user: AuthenticatedUser) {
    AboutScreenView(
        members = service.getMembers(),
        gameplayUrl = service.getGamePlayUrl(),
        titleScreenFunction = {navigator.goToTitleScreen(user)}
    )
}