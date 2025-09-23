package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

data class Player(val id: Int, val name: String)

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
    )
}

class LobbyCreationServiceImpl : LobbyCreationService {

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