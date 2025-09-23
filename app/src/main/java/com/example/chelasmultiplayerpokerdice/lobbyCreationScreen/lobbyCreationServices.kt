package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import kotlinx.coroutines.delay

data class Lobby(
    val id: Int,
    val name: String,
    val owner: String,
    val description: String,
    val rounds: Int,
    val isPrivate: Boolean,
    val password: String?,
    val playersCount: Int,
    val maxPlayers: Int
)
data class Player(val id: Int, val name: String)

interface CreateLobbyService {
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
    )
}

class CreateLobbyServiceImpl : CreateLobbyService {

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
    ) {
        TODO("Not yet implemented")

    }
}