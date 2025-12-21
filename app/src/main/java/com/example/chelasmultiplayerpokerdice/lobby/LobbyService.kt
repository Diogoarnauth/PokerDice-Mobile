package com.example.chelasmultiplayerpokerdice.lobby

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.LobbyDetails
import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyPlayersResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.UserMeResponse
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
    suspend fun fetchMe(token: String): Int
    suspend fun startGame(lobbyId: Int, token: String)


}
class LobbyServiceImpl(
    private val client: HttpClient
) : LobbyService {

    override suspend fun fetchLobbyDetails(lobbyId: Int): LobbyDetails {
        val lobbyDto: LobbyDto = client.get("$BASE_URL/lobbies/$lobbyId").body()

        Log.d(TAG, "lobbyDto $lobbyDto" )

        val playersResponse: LobbyPlayersResponseDto = client.get("$BASE_URL/users/obj/lobby/$lobbyId").body()

        Log.d(TAG, "playersResponse $playersResponse" )


        val lobby = Lobby(
            id = lobbyDto.id,
            name = lobbyDto.name,
            description = lobbyDto.description,
            hostId = lobbyDto.hostId,
            minUsers = lobbyDto.minUsers,
            maxUsers = lobbyDto.maxUsers,
            rounds = lobbyDto.rounds,
            minCreditToParticipate = lobbyDto.minCreditToParticipate,
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
           val leaved = client.delete("$BASE_URL/lobbies/$lobbyId/leave") {
                bearerAuth(token)
            }
            Log.d(TAG, "leaved $leaved")

        } catch (e: Exception) {
            Log.e("LOBBY_SERVICE", "Erro ao sair do lobby: ${e.message}")
        }
    }

    override suspend fun joinLobby(lobbyId: Int, token: String) {
        try {

            client.post("$BASE_URL/lobbies/$lobbyId/users") {
                bearerAuth(token)
            }
        } catch (e: Exception) {
            Log.e("LOBBY_SERVICE", "Erro ao entrar no lobby: ${e.message}")
            throw e
        }
    }

    override suspend fun fetchMe(token: String): Int {
        val response: UserMeResponse = client.get("$BASE_URL/users/getMe") {
            bearerAuth(token)
        }.body()

        return response.id
    }

    //TODO() tratamento de erros
    override suspend fun startGame(lobbyId: Int, token: String) {
        Log.d("startGame", "Iniciando startGame no LobbyService para lobbyId: $lobbyId")
        client.post("$BASE_URL/games/$lobbyId/start") {
            bearerAuth(token)
        }
    }

}