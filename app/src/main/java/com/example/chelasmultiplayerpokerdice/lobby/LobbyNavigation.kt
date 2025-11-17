package com.example.chelasmultiplayerpokerdice.lobby

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface LobbyNavigation {
    fun goToGameScreen(user: AuthenticatedUser, lobbyId: Int)
    fun goToTitleScreen(user: AuthenticatedUser)
}