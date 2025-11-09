package com.example.chelasmultiplayerpokerdice.lobbyCreation

import kotlinx.coroutines.delay
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase.tokens

interface LobbyCreationService {
    suspend fun createLobby(
        name: String,
        description: String,
        hostToken: String,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCreditToParticipate: Int
    ): Int
}

class LobbyCreationFakeServiceImpl() : LobbyCreationService {

    private val db = FakeDatabase

    override suspend fun createLobby(
        name: String,
        description: String,
        hostToken: String,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCreditToParticipate: Int
    ): Int {
        delay(1000)

        val hostId = tokens.find { it.tokenValidation == hostToken }?.userId
            ?: throw IllegalArgumentException("Token inválido, não foi possível criar o lobby")

        val newLobby = db.createLobby(
            name, description, hostId, minUsers, maxUsers, rounds, minCreditToParticipate
        )

        println("FAKE SERVICE: Lobby '$name' criado com sucesso pelo host $hostId!")

        return newLobby.id
    }
}