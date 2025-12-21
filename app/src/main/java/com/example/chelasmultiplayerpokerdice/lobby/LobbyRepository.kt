package com.example.chelasmultiplayerpokerdice.lobby

import com.example.chelasmultiplayerpokerdice.domain.LobbyDetails
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LobbyRepository(private val service: LobbyService) {

    fun getLobbyLive(lobbyId: Int): Flow<LobbyDetails> = flow {
        while (true) {
            try {
                val lobbyInfo = service.fetchLobbyDetails(lobbyId)
                emit(lobbyInfo)
            } catch (e: Exception) {
                throw e
            }
            delay(2000)
        }
    }

    suspend fun leaveLobby(lobbyId: Int, token: String) {
        service.abandonLobby(lobbyId, token)
    }

    suspend fun joinLobby(lobbyId: Int, token: String) {
        service.joinLobby(lobbyId, token)
    }

    suspend fun getMyId(token: String): Int {
        return service.fetchMe(token)
    }

    suspend fun startGame(lobbyId: Int, token: String) {
        service.startGame(lobbyId, token)
    }



}