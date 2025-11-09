package com.example.chelasmultiplayerpokerdice.login

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface LoginNavigation {

    fun goToSignupScreen()

    fun goToTitleScreen(user: AuthenticatedUser)
}