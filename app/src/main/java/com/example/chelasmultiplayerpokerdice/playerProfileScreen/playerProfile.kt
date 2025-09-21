package com.example.chelasmultiplayerpokerdice.playerProfileScreen
import androidx.compose.runtime.Composable

@Composable
fun PlayerProfile(service: ProfileService, navigator: ProfileNavigation) {
    PlayerProfileView(
        playerData = service.getPlayerProfileData(),
        goBackTitleScreenFunction = {navigator.goToTitleScreen()}
    )
}