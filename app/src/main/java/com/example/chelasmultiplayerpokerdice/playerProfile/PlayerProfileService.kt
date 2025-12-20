package com.example.chelasmultiplayerpokerdice.playerProfile

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.domain.remote.models.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders

interface PlayerProfileService {
   suspend fun getPlayerProfileData(token: String): User
    suspend fun getAppInvite(token: String): String
}

class PlayerProfileServiceImpl(
    private val client: HttpClient
) : PlayerProfileService {

    override suspend fun getPlayerProfileData(token: String): User {
        return try {
            val response: PlayerProfileResponseDto = client.get("$BASE_URL/users/getMe") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()

            // Mapeamento do DTO para o teu objeto de Domínio (User)
            User(
                id = response.id,
                username = response.username,
                name = response.name,
                age = response.age,
                credit = response.credit,
                winCounter = response.winCounter,
                lobbyId = response.lobbyId,
                passwordValidation = ""
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAppInvite(token: String): String {
        Log.d(TAG, "ANTES")
        val response: InviteAppOutputModel = client.post("$BASE_URL/invite") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
        Log.d(TAG, "Resposta do app invite $response")
        return response.inviteCode
    }
}