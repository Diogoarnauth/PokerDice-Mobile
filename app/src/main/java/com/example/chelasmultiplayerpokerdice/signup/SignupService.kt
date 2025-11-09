package com.example.chelasmultiplayerpokerdice.signup

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import kotlinx.coroutines.delay

interface SignupService {

    suspend fun signup(username: String, password: String, name: String, age: Int): AuthenticatedUser?
}

class SignupFakeServiceImpl : SignupService {
    private val db = FakeDatabase

    override suspend fun signup(username: String, password: String, name: String, age: Int): AuthenticatedUser? {
        delay(1000)
        val token = db.signup(username, password, name, age)

        return if (token != null ) {
            AuthenticatedUser(username, token.tokenValidation)
        } else {
            null
        }
    }
}