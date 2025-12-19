package com.example.chelasmultiplayerpokerdice.lobbies

import android.util.Log
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.LobbyInfo
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface LobbiesService {
    suspend fun fetchLobbiesList(): List<LobbyInfo>
    fun getGamePlayUrl(): String
}
class LobbiesServiceImpl(
    private val client: HttpClient
) : LobbiesService {

    override suspend fun fetchLobbiesList(): List<LobbyInfo> {
        return try {

            val lobbyDtos: List<LobbyDto> = client.get("lobbies").body()
            Log.d(TAG, "lobbies obtidos $lobbyDtos")


            lobbyDtos.map { dto ->
                val lobby = Lobby(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description,
                    hostId = dto.hostId,
                    minUsers = dto.minUsers,
                    maxUsers = dto.maxUsers,
                    rounds = dto.rounds,
                    minCreditToParticipate = dto.minCreditToParticipate,
                    isRunning = false
                )
                LobbyInfo(lobby, playerCount = 0)
            }
        } catch (e: Exception) {
            Log.e("LOBBIES_SERVICE", "Erro ao buscar lobbies: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    override fun getGamePlayUrl(): String {
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }
}