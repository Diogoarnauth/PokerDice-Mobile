package com.example.chelasmultiplayerpokerdice.lobby

import android.util.Log
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.LobbyDetails
import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyPlayersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post


interface LobbyService {
    suspend fun fetchLobbyDetails(lobbyId: Int): LobbyDetails
    suspend fun abandonLobby(lobbyId: Int, token: String)

    suspend fun joinLobby(lobbyId: Int, token: String)

}
class LobbyServiceImpl(
    private val client: HttpClient
) : LobbyService {

    override suspend fun fetchLobbyDetails(lobbyId: Int): LobbyDetails {
        val lobbyDto: LobbyDto = client.get("lobbies/$lobbyId").body()

        val playersResponse: LobbyPlayersResponseDto = client.get("users/obj/lobby/$lobbyId").body()

        val lobby = Lobby(
            id = lobbyDto.id,
            name = lobbyDto.name,
            description = lobbyDto.description,
            hostId = lobbyDto.hostId,
            minUsers = lobbyDto.minUsers,
            maxUsers = lobbyDto.maxUsers,
            rounds = lobbyDto.rounds,
            minCreditToParticipate = lobbyDto.minCreditToParticipate,
            isRunning = false
        )

        val users = playersResponse.players.map { playerDto ->
            User(
                id = playerDto.id,
                username = playerDto.username,
                passwordValidation = "",
                name = playerDto.name,
                age = playerDto.age,
                credit = playerDto.credit,
                winCounter = playerDto.winCounter,
                lobbyId = playerDto.lobbyId
            )
        }

        return LobbyDetails(lobby, users)
    }

    override suspend fun abandonLobby(lobbyId: Int, token: String) {
        try {
            // TODO ("FALTA CONFIRMAR SE É O ÚLTIMO OU NO)
            client.delete("lobbies/$lobbyId/leave") {
                bearerAuth(token)
            }
        } catch (e: Exception) {
            Log.e("LOBBY_SERVICE", "Erro ao sair do lobby: ${e.message}")
        }
    }

    override suspend fun joinLobby(lobbyId: Int, token: String) {
        try {

            client.post("lobbies/$lobbyId/users") {
                bearerAuth(token)
            }
        } catch (e: Exception) {
            Log.e("LOBBY_SERVICE", "Erro ao entrar no lobby: ${e.message}")
            throw e
        }
    }
}