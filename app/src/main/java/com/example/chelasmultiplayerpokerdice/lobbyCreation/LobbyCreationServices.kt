package com.example.chelasmultiplayerpokerdice.lobbyCreation

import kotlinx.coroutines.delay

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

        println("FAKE SERVICE: Lobby '$name' criado com sucesso!")
        println("Descrição: $description | Jogadores min: $minUsers | Rounds: $rounds")

        // devolve um ID aleatório só para simular criação
        return (1..9999).random()
    }
}