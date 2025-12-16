package com.example.chelasmultiplayerpokerdice.auth.login

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface LoginNavigation {

    fun goToSignupScreen()

    fun goToTitleScreen(user: AuthenticatedUser)
}