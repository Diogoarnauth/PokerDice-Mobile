package com.example.chelasmultiplayerpokerdice.lobbyScreen

interface LobbyScreenService {
    fun getLobby(): Lobby

}

class LobbyScreenServiceImpl : LobbyScreenService {

    override fun getLobby(): Lobby {
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