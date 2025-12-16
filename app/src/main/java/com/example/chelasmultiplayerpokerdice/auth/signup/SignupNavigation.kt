package com.example.chelasmultiplayerpokerdice.auth.signup

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser

interface SignupNavigation {
    fun goToLoginScreen()
    fun goToTitleScreen(user: AuthenticatedUser)
}