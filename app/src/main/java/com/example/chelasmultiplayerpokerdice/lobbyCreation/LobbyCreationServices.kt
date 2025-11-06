package com.example.chelasmultiplayerpokerdice.lobbyCreation

import kotlinx.coroutines.delay
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase

interface LobbyCreationService {
    suspend fun createLobby(
        name: String,
        description: String,
        hostId: Int,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCreditToParticipate: Int
    ): Int // devolve o ID
}

class LobbyCreationFakeServiceImpl() : LobbyCreationService {

    private val db = FakeDatabase

    override suspend fun createLobby(
        name: String,
        description: String,
        hostId: Int,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCreditToParticipate: Int
    ): Int {
        delay(1000)

        val newLobby = db.createLobby(
            name, description, hostId, minUsers, maxUsers, rounds, minCreditToParticipate
        )

        println("FAKE SERVICE: Lobby '$name' criado com sucesso!")
        println("Descrição: $description | Jogadores min: $minUsers | Rounds: $rounds")

        // devolve um ID aleatório só para simular criação
        return newLobby.id
    }
}