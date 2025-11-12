package com.example.chelasmultiplayerpokerdice.signup

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import kotlinx.coroutines.delay

interface SignupService {

    suspend fun signup(username: String, password: String, name: String, age: Int): AuthenticatedUser?
}

class SignupFakeServiceImpl : SignupService {
    private val db = FakeDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun signup(username: String, password: String, name: String, age: Int): AuthenticatedUser? {
        delay(1000)
        println("antes do token")
        val token = db.signup(username, password, name, age)
        println("token $token")
        return if (token != null ) {
            println("entrou no if")
            AuthenticatedUser(username, token.tokenValidation)
        } else {
            null
        }
    }
}