package com.example.chelasmultiplayerpokerdice.playerProfileScreen
import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.Lobbies.LobbiesNavigation
import com.example.chelasmultiplayerpokerdice.Lobbies.LobbiesService
import com.example.chelasmultiplayerpokerdice.Lobbies.LobbiesView

@Composable
fun Lobbies(service: LobbiesService, navigator: LobbiesNavigation) {
    LobbiesView(
        lobbies =  service.getLobbies(),
        goBackTitleScreenFunction = {navigator.goToTitleScreen()},
        createLobbyFunction = {navigator.goToLobbyCreationScreen()},
        selectLobbyFunction = {navigator.goToLobbyDetailsScreen(0)}
    )
}