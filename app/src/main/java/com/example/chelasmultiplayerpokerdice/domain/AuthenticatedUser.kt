package com.example.chelasmultiplayerpokerdice.domain

import java.io.Serializable

data class AuthenticatedUser(
    val username: String,
    val token: String?
): Serializable