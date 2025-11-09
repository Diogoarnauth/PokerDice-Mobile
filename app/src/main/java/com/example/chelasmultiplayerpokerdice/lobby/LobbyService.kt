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
    fun abandonLobby(lobbyId: Int, token: String?)

}

class LobbyFakeServiceImpl : LobbyService {

    private val db = FakeDatabase

    override fun getLobby(lobbyId: Int): Flow<Lobby> {
        return db.lobbies.map { listaDeLobbies ->
            listaDeLobbies.find { it.id == lobbyId }
        }
            .filterNotNull()
    }

    override fun abandonLobby(lobbyId: Int, token: String?) {

        val userToken = tokens.find { it.tokenValidation == token }
        if (userToken != null) {
            val userId = userToken.userId
            db.abandonLobby(lobbyId, userId)
        } else {
            TODO()
        }
    }
}
