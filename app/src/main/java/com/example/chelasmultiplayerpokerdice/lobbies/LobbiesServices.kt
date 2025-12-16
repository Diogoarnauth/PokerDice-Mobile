package com.example.chelasmultiplayerpokerdice.lobbies

import com.example.chelasmultiplayerpokerdice.domain.LobbyInfo
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import kotlinx.coroutines.delay

interface LobbiesService {
    suspend fun fetchLobbiesList(): List<LobbyInfo>
    fun getGamePlayUrl(): String
}

class LobbiesFakeServiceImpl : LobbiesService {
    private val db = FakeDatabase

    override suspend fun fetchLobbiesList(): List<LobbyInfo> {

        delay(500)

        val currentLobbies = db.lobbies.value
        val currentUsers = db.usersFlow.value

        return currentLobbies.map { lobby ->
            val count = currentUsers.count { it.lobbyId == lobby.id }
            LobbyInfo(lobby, count)
        }
    }

    override fun getGamePlayUrl(): String {
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }
}