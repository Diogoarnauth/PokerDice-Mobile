package com.example.chelasmultiplayerpokerdice.lobbyCreation

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface LobbyCreationNavigation {
    fun goToLobbiesScreen(user: AuthenticatedUser)
    fun goToLobbyDetailsScreen(user: AuthenticatedUser, lobbyId: Int)
}