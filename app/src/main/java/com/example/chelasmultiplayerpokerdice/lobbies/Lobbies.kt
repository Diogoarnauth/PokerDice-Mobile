package com.example.chelasmultiplayerpokerdice.lobbies
import androidx.compose.runtime.Composable

@Composable
fun Lobbies(service: LobbiesService, navigator: LobbiesNavigation) {
    LobbiesView(
        lobbies =  service.getLobbies(),
        goBackTitleScreenFunction = {navigator.goToTitleScreen()},
        createLobbyFunction = {navigator.goToLobbyCreationScreen()},
        selectLobbyFunction = {navigator.goToLobbyDetailsScreen(0)}
    )
}