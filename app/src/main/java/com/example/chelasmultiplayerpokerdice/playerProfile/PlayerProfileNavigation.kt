package com.example.chelasmultiplayerpokerdice.playerProfile

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface PlayerProfileNavigation {
    fun goToTitleScreen(user: AuthenticatedUser)
    //fun goToLoginScreen()
}