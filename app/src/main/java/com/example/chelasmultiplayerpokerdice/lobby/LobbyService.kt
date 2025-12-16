package com.example.chelasmultiplayerpokerdice.lobby

import com.example.chelasmultiplayerpokerdice.domain.LobbyDetails
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase.tokens
import kotlinx.coroutines.delay

interface LobbyService {
    suspend fun fetchLobbyDetails(lobbyId: Int): LobbyDetails
    suspend fun abandonLobby(lobbyId: Int, token: String)

    suspend fun joinLobby(lobbyId: Int, token: String)

}

class LobbyFakeServiceImpl : LobbyService {

    private val db = FakeDatabase

    override suspend fun fetchLobbyDetails(lobbyId: Int): LobbyDetails {
        val currentLobbies = db.lobbies.value

        val lobby = currentLobbies.find { it.id == lobbyId }
            ?: throw Exception("Lobby not found")

        val players = db.usersFlow.value.filter { it.lobbyId == lobbyId }

        return LobbyDetails(lobby, players)
    }


    override suspend fun abandonLobby(lobbyId: Int, token: String) {

        delay(500)

        val userToken = tokens.find { it.tokenValidation == token }
        if (userToken != null) {
            val userId = userToken.userId
            db.abandonLobby(lobbyId, userId)
        } else {
            TODO()
        }
    }

    override suspend fun joinLobby(lobbyId: Int, token: String) {

        delay(250)

        val userToken = tokens.find { it.tokenValidation == token }
        if (userToken != null) {
            val userId = userToken.userId
            db.joinLobby(lobbyId, userId)
        }
    }
}
