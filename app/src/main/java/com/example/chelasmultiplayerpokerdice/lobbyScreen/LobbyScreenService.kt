package com.example.chelasmultiplayerpokerdice.lobbyScreen

import kotlinx.coroutines.delay

interface LobbyScreenService {
    suspend fun getLobby(): Lobby
}

class LobbyScreenFakeServiceImpl : LobbyScreenService {

    override suspend fun getLobby(): Lobby {
        delay(1000) // Simula atraso de rede

        return Lobby(
            id = 1,
            name = "Poker Masters",
            owner = "Renata",
            description = "Lobby para testar a sorte 🎲",
            rounds = 12,
            isPrivate = false,
            password = null,
            playersCount = 3,
            maxPlayers = 4,
            players = listOf(
                Player(1, "Renata"),
                Player(2, "Diogo"),
                Player(3, "Humberto")
            )
        )
    }
}
