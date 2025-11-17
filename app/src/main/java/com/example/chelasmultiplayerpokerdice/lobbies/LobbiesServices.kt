package com.example.chelasmultiplayerpokerdice.lobbies

import com.example.chelasmultiplayerpokerdice.domain.LobbyInfo
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface LobbiesService {
    fun getLobbiesInfo(): Flow<List<LobbyInfo>>
    fun getGamePlayUrl(): String
}

class LobbiesFakeServiceImpl : LobbiesService {
    private val db = FakeDatabase

    override fun getLobbiesInfo(): Flow<List<LobbyInfo>> {
        return db.lobbies.combine(db.usersFlow) { lobbies, users ->
            lobbies.map { lobby ->
                val count = users.count { it.lobbyId == lobby.id }
                LobbyInfo(lobby, count)
            }
        }
    }


    override fun getGamePlayUrl(): String {
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }
}