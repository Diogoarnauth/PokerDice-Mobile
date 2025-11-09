package com.example.chelasmultiplayerpokerdice.lobbies

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface LobbiesNavigation {

    fun goToLobbyDetailsScreen(user: AuthenticatedUser, lobbyId: Int)
    fun goToLobbyCreationScreen(user: AuthenticatedUser)
    fun goToTitleScreen()

}