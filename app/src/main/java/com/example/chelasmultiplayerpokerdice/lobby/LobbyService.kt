package com.example.chelasmultiplayerpokerdice.lobby

import com.example.chelasmultiplayerpokerdice.domain.*
import kotlinx.coroutines.delay

interface LobbyService {
    suspend fun getLobby(): Lobby
}

class LobbyFakeServiceImpl : LobbyService {

    override suspend fun getLobby(): Lobby {
        delay(1000) // Simula atraso de rede

        return Lobby(
            id = 1,
            name = "Poker Masters",
            description = "Lobby para testar a sorte 🎲",
            hostId = 1,
            minUsers = 2,
            maxUsers = 4,
            rounds = 12,
            minCreditToParticipate = 0,
            playersCount = 3,
            players = listOf(
                Player(1, "Renata"),
                Player(2, "Diogo"),
                Player(3, "Humberto")
            )
        )
    }
}
