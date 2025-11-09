package com.example.chelasmultiplayerpokerdice.title

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface TitleNavigation {
    fun goToLobbiesScreen(user: AuthenticatedUser)
    fun goToPlayerProfileScreen(user: AuthenticatedUser)
    fun goToAboutScreen(user: AuthenticatedUser)
}
