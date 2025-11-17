package com.example.chelasmultiplayerpokerdice.game

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface GameNavigation {
    fun goToTitleScreen(user: AuthenticatedUser)

    //fun abandonGame(user: AuthenticatedUser, gameId: Int) // TODO("VER SE FAZ SENTIDO")
}