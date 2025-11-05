package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import kotlinx.coroutines.delay

// ---------- Modelo ----------
data class Player(val id: Int, val name: String)

// ---------- Interface ----------
interface LobbyCreationService {
    suspend fun createLobby(
        id: Int,
        name: String,
        owner: String,
        description: String,
        rounds: Int,
        isPrivate: Boolean,
        password: String?,
        playersCount: Int,
        maxPlayers: Int,
        players: List<Player>
    ): Int // devolve o ID do novo lobby
}

// ---------- Fake Implementation ----------
class LobbyCreationFakeServiceImpl : LobbyCreationService {

    override suspend fun createLobby(
        id: Int,
        name: String,
        owner: String,
        description: String,
        rounds: Int,
        isPrivate: Boolean,
        password: String?,
        playersCount: Int,
        maxPlayers: Int,
        players: List<Player>
    ): Int {
        delay(1000)

        println("FAKE SERVICE: Lobby '$name' criado com sucesso!")
        println("Descrição: $description | Jogadores: $playersCount/$maxPlayers | Rounds: $rounds")

        // devolve um ID aleatório só para simular criação
        return (1..9999).random()
    }
}
