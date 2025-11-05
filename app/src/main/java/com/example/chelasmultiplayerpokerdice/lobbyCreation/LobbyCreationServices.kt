package com.example.chelasmultiplayerpokerdice.lobbyCreation

import com.example.chelasmultiplayerpokerdice.domain.Player
import kotlinx.coroutines.delay

// ---------- Modelo ----------

// ---------- Interface ----------
interface LobbyCreationService {
    suspend fun createLobby(
        id: Int,
        name: String,
        hostId: Int,
        description: String,
        rounds: Int,
        minUsers: Int,
        maxUsers: Int,
        minCreditToParticipate: Int,
        playersCount: Int,
        players: List<Player>
    ): Int
}

// ---------- Fake Implementation ----------
class LobbyCreationFakeServiceImpl : LobbyCreationService {

    override suspend fun createLobby(
        id: Int,
        name: String,
        hostId: Int,
        description: String,
        rounds: Int,
        minUsers: Int,
        maxUsers: Int,
        minCreditToParticipate: Int,
        playersCount: Int,
        players: List<Player>
    ): Int {
        delay(1000)

        println("FAKE SERVICE: Lobby '$name' criado com sucesso!")
        println("Descrição: $description | Jogadores: $playersCount | Rounds: $rounds")

        // devolve um ID aleatório só para simular criação
        return (1..9999).random()
    }
}
