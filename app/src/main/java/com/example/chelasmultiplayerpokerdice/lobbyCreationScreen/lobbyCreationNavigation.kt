package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

interface CreateLobbyNavigation {
    fun goToLobbiesScreen()
    fun goToLobbyCreationScreen()
    fun goToLobbyDetailsScreen(lobbyId: Int)
}