package com.example.chelasmultiplayerpokerdice.playerProfile

import kotlinx.coroutines.delay

interface PlayerProfileService {
    suspend fun getPlayerProfileData(username: String): PlayerProfileData
}

class PlayerProfileFakeServiceImpl : PlayerProfileService {

    override suspend fun getPlayerProfileData(username: String): PlayerProfileData {
        delay(1000)

        return when (username.lowercase()) {
            "renata1234" -> PlayerProfileData("renata1999234", "Renata Castanheira", 19)
            "diogo99" -> PlayerProfileData("diogo99", "Diogo Arnauth", 22)
            "humberto" -> PlayerProfileData("humberto", "Humberto Carvalho", 25)
            else -> PlayerProfileData(username, "Jogador Desconhecido", 0)
        }
    }
}
