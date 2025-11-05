package com.example.chelasmultiplayerpokerdice.playerProfileScreen
import androidx.compose.runtime.Composable
/*
@Composable
fun PlayerProfile(service: ProfileService, navigator: ProfileNavigation) {
    PlayerProfileView(
        playerData = service.getPlayerProfileData(),
        goBackTitleScreenFunction = {navigator.goToTitleScreen()}
    )
}*/


import androidx.compose.material3.Text
import kotlinx.coroutines.delay


interface ProfileService {
    suspend fun getPlayerProfileData(username: String): PlayerProfileData
}

class ProfileFakeServiceImpl : PlayerProfileService {

    override suspend fun getPlayerProfileData(username: String): PlayerProfileData {
        delay(1000) // simula tempo de rede

        // dados simulados diferentes conforme o username
        return when (username.lowercase()) {
            "renata1234" -> PlayerProfileData("renata1234", "Renata Castanheira", 19)
            "diogo99" -> PlayerProfileData("diogo99", "Diogo Arnauth", 22)
            else -> PlayerProfileData(username, "Jogador Desconhecido", 0)
        }
    }
}