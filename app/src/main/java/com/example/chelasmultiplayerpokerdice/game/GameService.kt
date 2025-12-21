package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.remote.models.DieDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.GameDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyPlayersResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.ReRollResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.RollResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.TurnDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.toGameState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface GameService {
    suspend fun getInitialGameState(lobbyId: Int, token: String): GameState
    suspend fun rollDice(lobbyId: Int, token: String): List<DieDto>
    suspend fun rerollDice(lobbyId: Int, token: String, dicePositionsMask: List<Int>): List<DieDto>

    suspend fun endTurn(lobbyId: Int, token: String): String
    suspend fun startNextRound(lobbyId: Int, token: String): GameState
    suspend fun fetchFullGameState(lobbyId: Int, token: String): GameState
}

class GameRemoteServiceImpl(
    private val client: HttpClient
) : GameService {

    override suspend fun getInitialGameState(lobbyId: Int, token: String): GameState {
        Log.d("GAME_SERVICE", "A pedir dados do JOGO para lobby $lobbyId")
        val gameDto: GameDto = client.get("$BASE_URL/games/lobby/$lobbyId") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()

        Log.d("GAME_SERVICE", "A pedir lista de JOGADORES...")
        val playersResponse: LobbyPlayersResponseDto =
            client.get("$BASE_URL/users/obj/lobby/$lobbyId") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()

        return gameDto.toGameState(playersResponse.players)
    }

    override suspend fun fetchFullGameState(lobbyId: Int, token: String): GameState {
        val gameDto: GameDto = client.get("$BASE_URL/games/lobby/$lobbyId") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()

        val playersResponse: LobbyPlayersResponseDto =
            client.get("$BASE_URL/users/obj/lobby/$lobbyId") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()

        val turnDto: TurnDto? = try {
            val response = client.get("$BASE_URL/games/${gameDto.id}/getCurrentTurn") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            if (response.status == HttpStatusCode.OK) response.body() else null
        } catch (e: Exception) {
            Log.w(TAG, "Turno não encontrado (inicio de jogo ou fim de ronda): ${e.message}")
            null
        }

        return gameDto.toGameState(playersResponse.players)
    }


        override suspend fun rollDice(lobbyId: Int, token: String): List<DieDto> {
            // 1. Recebe o objeto {"dice": "J,J,9,K,J"}
            val response: RollResponseDto = client.post("$BASE_URL/games/$lobbyId/roll") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()

            Log.d(TAG, "<ROLL> String de dados: ${response.dice}")

            // 2. Transforma "J,J,9,K,J" numa List<DieDto>
            // Usamos o index como ID temporário (0 a 4)
            return response.dice.split(",").mapIndexed { index, faceLabel ->
                DieDto(
                    id = index,
                    face = faceLabel,
                    held = false
                )
            }
        }



    /*
        override suspend fun rerollDice(lobbyId: Int, token: String, mask: List<Int>): List<DieDto> {
            Log.d(TAG, "REROLL mask = $mask")
            val responseString: String = client.post("$BASE_URL/games/$lobbyId/reroll") {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(mask)
            }.body()

            Log.d(TAG, "REROLL response = $responseString")

            return responseString.dice.split
            //return Json.decodeFromString<List<DieDto>>(responseString)
        }
    */

    /*
    override suspend fun rollDice(
        lobbyId: Int,
        token: String
    ): List<DieDto> {
        val response: RollResponseDto = client.post("$BASE_URL/games/$lobbyId/roll") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()

        Log.d(TAG, "<ROLL> dados: ${response.dice}")

        return response.dice.mapIndexed { index, faceLabel ->
            DieDto(
                id = index,
                face = faceLabel,
                held = false
            )
        }
    }
     */

    override suspend fun rerollDice(
        lobbyId: Int,
        token: String,
        mask: List<Int>
    ): List<DieDto> {
        Log.d(TAG, "REROLL mask = $mask")

        val response: ReRollResponseDto = client.post("$BASE_URL/games/$lobbyId/reroll") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(mask)
        }.body()

        Log.d(TAG, "REROLL dados: ${response.dice}")

        return response.dice.mapIndexed { index, faceLabel ->
            DieDto(
                id = index,
                face = faceLabel,
                held = false
            )
        }
    }


    //TODO() validar se funciona
    override suspend fun endTurn(gameId: Int, token: String): String {
        return client.post("$BASE_URL/games/$gameId/end") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }


    //TODO() validar se funciona
    override suspend fun startNextRound(lobbyId: Int, token: String): GameState {
        // Se o teu backend muda de ronda automaticamente ou via endpoint específico
        // Aqui apenas refrescamos o estado para obter a nova roundNumber
        return getInitialGameState(lobbyId, token)
    }


}