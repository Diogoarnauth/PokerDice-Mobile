package com.example.chelasmultiplayerpokerdice.auth.login

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import kotlinx.coroutines.delay

interface LoginService {
    suspend fun login(username: String, password: String): AuthenticatedUser?
}

class LoginFakeServiceImpl : LoginService {
    private val db = FakeDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun login(username: String, password: String): AuthenticatedUser? {
        delay(1000)
        val token = db.login(username, password)
        return if (token != null) {
            AuthenticatedUser(username, token.tokenValidation)
        } else {
            null
        }
    }
}