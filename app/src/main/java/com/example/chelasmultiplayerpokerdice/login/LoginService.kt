package com.example.chelasmultiplayerpokerdice.login

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import kotlinx.coroutines.delay

interface LoginService {

    suspend fun login(username: String, password: String): String?
}

class LoginFakeServiceImpl : LoginService {
    private val db = FakeDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun login(username: String, password: String): String? {
        delay(1000)
        val token = db.login(username, password)
        return token?.tokenValidation
    }
}