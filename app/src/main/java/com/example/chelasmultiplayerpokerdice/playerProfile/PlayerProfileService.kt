package com.example.chelasmultiplayerpokerdice.playerProfile

/*interface ProfileService {

    fun getPlayerProfileData(): PlayerProfileData
    fun getPlayerName(): String

    fun getPlayerUsername(): String

    fun getPlayerAge(): Int

}

class ProfileServiceImpl : ProfileService {

    override fun getPlayerName(): String {
        return getPlayerProfileData().playerName
    }

    override fun getPlayerUsername(): String {
        return getPlayerProfileData().playerUsername
    }

    override fun getPlayerAge(): Int {
        return getPlayerProfileData().playerAge
    }
    override fun getPlayerProfileData(): PlayerProfileData {
        return PlayerProfileData("renata1234", "Renata Castanheira", 19)
    }


}*/
import kotlinx.coroutines.delay

// ---------- Interface ----------
interface PlayerProfileService {
    suspend fun getPlayerProfileData(username: String): PlayerProfileData
}

// ---------- Fake Implementation ----------
class PlayerProfileFakeServiceImpl : PlayerProfileService {

    override suspend fun getPlayerProfileData(username: String): PlayerProfileData {
        delay(1000) // Simula tempo de rede

        // Devolve perfis diferentes conforme o username
        return when (username.lowercase()) {
            "renata1234" -> PlayerProfileData("renata1999234", "Renata Castanheira", 19)
            "diogo99" -> PlayerProfileData("diogo99", "Diogo Arnauth", 22)
            "humberto" -> PlayerProfileData("humberto", "Humberto Carvalho", 25)
            else -> PlayerProfileData(username, "Jogador Desconhecido", 0)
        }
    }
}
