package com.example.chelasmultiplayerpokerdice.lobbyCreation

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.TAG
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyCreateRequestDto
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.math.max

interface LobbyCreationService {
    suspend fun createLobby(
        name: String,
        description: String,
        hostToken: String,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCreditToParticipate: Int
    )
}
class LobbyCreationServiceImpl(
    private val client: HttpClient
) : LobbyCreationService {

    override suspend fun createLobby(
        name: String,
        description: String,
        hostToken: String,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCreditToParticipate: Int
    ) {
        Log.d(TAG, "name $name, decription $description, minUsers $minUsers, maxUsers $maxUsers, rounds $rounds, minCredits $minCreditToParticipate,")
        // Criamos o corpo do pedido
        val requestBody = LobbyCreateRequestDto(
            name = name,
            description = description,
            minUsers = minUsers,
            maxUsers = maxUsers,
            rounds = rounds,
            minCreditToParticipate = minCreditToParticipate,
            turnTime = 2
        )

        val response = client.post("$BASE_URL/lobbies") {
            header(HttpHeaders.Authorization, "Bearer $hostToken")
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }

        Log.d(TAG, "diz me a resposta $response")

        if (response.status.value == 201) {
            return
        } else {
            // 1. Extraímos o texto puro do erro (o JSON que mostraste)
            val errorText = response.bodyAsText()
            Log.d(TAG, "JSON REAL DO ERRO: $errorText")

            // 2. Tentamos extrair apenas a "message" para não mostrar o JSON feio ao user
            val messageToShow = try {
                val json = Json.parseToJsonElement(errorText).jsonObject
                json["message"]?.jsonPrimitive?.content ?: response.status.description

            } catch (e: Exception) {
                response.status.description
            }

            throw Exception(messageToShow)
        }
    }
}