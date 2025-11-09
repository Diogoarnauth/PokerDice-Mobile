package com.example.chelasmultiplayerpokerdice.about

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface AboutNavigation {
    fun goToTitleScreen(user: AuthenticatedUser)
}