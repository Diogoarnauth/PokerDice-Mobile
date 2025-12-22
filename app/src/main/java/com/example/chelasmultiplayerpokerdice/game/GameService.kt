package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.DiceFace
import com.example.chelasmultiplayerpokerdice.domain.Die
import com.example.chelasmultiplayerpokerdice.domain.remote.models.DieDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.GameDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyPlayersResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.PlayerDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.ReRollResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.RollResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.TurnDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.WinnersResponseDto
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

interface GameService {
    suspend fun getInitialGameState(lobbyId: Int, token: String): GameState
    suspend fun rollDice(lobbyId: Int, token: String): List<DieDto>
    suspend fun rerollDice(lobbyId: Int, token: String, dicePositionsMask: List<Int>): List<DieDto>

    suspend fun endTurn(lobbyId: Int, token: String): String
    suspend fun startNextRound(lobbyId: Int, token: String): GameState
    suspend fun fetchFullGameState(gameState: GameState, lobbyId: Int, token: String): GameState

    suspend fun checkWinner(lobbyId: Int, token: String): List<String>

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

    override suspend fun checkWinner(gameId: Int, token: String): List<String> {
        val resp = client.get("$BASE_URL/games/$gameId/winners") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        val dto: WinnersResponseDto = resp.body()

        return dto.winners
    }

    override suspend fun fetchFullGameState(
        gameState: GameState,
        lobbyId: Int,
        token: String
    ): GameState {
        return try {
            val response = client.get("$BASE_URL/games/lobby/$lobbyId") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            if (response.status == HttpStatusCode.NotFound) {
                Log.d(TAG, "Jogo não encontrado. A verificar vencedores finais...")

                return try {
                    val winnersUsernames = checkWinner(gameState.id, token)

                    Log.d(" winnersUsers", "Vencedores finais: $winnersUsernames")
                    val finalWinners = gameState.players.filter { player ->
                        winnersUsernames.contains(player.name)
                    }

                    Log.d("finalWinners", "Vencedores finais encontrados: $finalWinners")

                    gameState.copy(
                        finalWinners = finalWinners,
                        canRoll = false
                    )

                } catch (e: Exception) {
                    Log.e(TAG, "Erro crítico: Jogo e Vencedores não encontrados.")
                    throw Exception("Erro de integridade: Jogo inexistente sem registo de vencedores.")
                }
            }

            val gameDto: GameDto = response.body()
            val playersResponse: LobbyPlayersResponseDto =
                client.get("$BASE_URL/users/obj/lobby/$lobbyId") {
                    header(HttpHeaders.Authorization, "Bearer $token")
                }.body()

            val turnDto: TurnDto? = try {
                val tResponse = client.get("$BASE_URL/games/${gameDto.id}/getCurrentTurn") {
                    header(HttpHeaders.Authorization, "Bearer $token")
                }
                if (tResponse.status == HttpStatusCode.OK) tResponse.body() else null
            } catch (e: Exception) {
                null
            }

            buildGameStateFromParts(gameState, gameDto, playersResponse.players, turnDto)

        } catch (e: Exception) {
            if (e.message?.contains("Erro de integridade") == true) throw e
            Log.e(TAG, "Erro de rede no polling: ${e.message}")
            throw e
        }
    }

    private fun buildGameStateFromParts(
        gameState: GameState,
        game: GameDto,
        lobbyPlayers: List<PlayerDto>,
        turn: TurnDto?
    ): GameState {
        Log.d(TAG, "<TURN> turn $turn")
        Log.d(TAG, "<BANANOCAS> game $game")
        Log.d(TAG, "<BANANOCAS> lobbyPlayers $lobbyPlayers")

        val diceFromTurn = if (!turn?.diceFaces.isNullOrBlank()) {
            Log.d(TAG, "<TURN> ENTREI ")

            turn!!.diceFaces!!.split(",").mapIndexed { index, face ->
                Die(id = index, face = DiceFace.fromLabel(face), isHeld = false)
            }
        } else {
            emptyList()
        }
        Log.d(TAG, "<TURN> diceFromTurn $diceFromTurn")
        val isNewRound = game.roundCounter > gameState.roundNumber

        val playerStatusList = lobbyPlayers.map { player ->
            val isHisTurn = turn?.playerId == player.id
            PlayerStatus(
                id = player.id,
                name = player.username,
                dice = if (isHisTurn) diceFromTurn
                else if (isNewRound) null
                else gameState.players.find { it.id == player.id }?.dice,
                isCurrentTurn = isHisTurn,
                hand = null
            )
        }

        // Procuramos o nome do jogador atual para a UI
        val currentPlayer = playerStatusList.find { it.isCurrentTurn }

        return GameState(
            id = game.id,
            lobbyId = game.lobbyId,
            dice = diceFromTurn, // Os dados que aparecem no centro do ecrã
            players = playerStatusList,
            currentPlayerName = currentPlayer?.name ?: "A aguardar...",
            rollsLeft = if (turn != null) 3 - turn.rollCount else 3,
            roundNumber = game.roundCounter,
            canRoll = true,
            roundWinners = emptyList(),
            finalWinners = if (game.state == "FINISHED") playerStatusList else emptyList()
        )
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