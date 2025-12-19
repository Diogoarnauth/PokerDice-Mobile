package com.example.chelasmultiplayerpokerdice.auth.login

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LoginRequestDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LoginResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

interface LoginService {
    suspend fun login(username: String, password: String): AuthenticatedUser?
}

class LoginServiceImpl(
    private val client: HttpClient
) : LoginService {

    override suspend fun login(username: String, password: String): AuthenticatedUser? {
        Log.d(TAG, "🚀 Iniciando Login para user: '$username'")
        return try {
            val response: LoginResponseDto = client.post("$BASE_URL/users/token") {
                header("Content-Type", "application/json")
                setBody(LoginRequestDto(username, password))

            }.body()
            AuthenticatedUser(username, response.token)

        } catch (e: Exception) {
            Log.e(TAG, "❌ ERRO CRÍTICO NO LOGIN!")
            Log.e(TAG, "Mensagem: ${e.message}")
            Log.e(TAG, "Causa: ${e.cause}")
            e.printStackTrace()
            null
        }
    }
}