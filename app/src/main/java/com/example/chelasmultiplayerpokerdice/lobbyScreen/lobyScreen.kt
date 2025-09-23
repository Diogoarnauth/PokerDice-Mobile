package com.example.chelasmultiplayerpokerdice.lobbyScreen
import androidx.compose.runtime.Composable


@Composable
fun LobbyScreen(service: LobbyScreenService, navigator: LobbyNavigation) {
    LobbyScreenView(
        lobby= service.getLobby(),
        onAbandon= {navigator.goToLobbiesScreen()} ,
        onStartGame= {navigator.goToGameScreen()}
    )
}