package com.example.chelasmultiplayerpokerdice.lobby

import com.example.chelasmultiplayerpokerdice.domain.*
import kotlinx.coroutines.delay
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase.tokens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

interface LobbyService {
    fun getLobby(lobbyId: Int): Flow<Lobby>
    suspend fun abandonLobby(lobbyId: Int, token: String)

    fun getLobbyPlayersFlow(lobbyId: Int): Flow<List<User>>

    suspend fun joinLobby(lobbyId: Int, token: String)

}

class LobbyFakeServiceImpl : LobbyService {

    private val db = FakeDatabase

    override fun getLobby(lobbyId: Int): Flow<Lobby> {
        return db.lobbies.map { listaDeLobbies ->
            listaDeLobbies.find { it.id == lobbyId }
        }
            .filterNotNull()
    }

    override fun getLobbyPlayersFlow(lobbyId: Int): Flow<List<User>> =
        db.usersFlow.map { usersList ->
            usersList.filter { it.lobbyId == lobbyId }
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
