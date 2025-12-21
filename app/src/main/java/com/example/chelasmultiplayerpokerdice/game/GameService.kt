package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.domain.remote.models.GameDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.LobbyPlayersResponseDto
import com.example.chelasmultiplayerpokerdice.domain.remote.models.toGameState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

interface GameService {
    suspend fun getInitialGameState(lobbyId: Int, token: String): GameState
    suspend fun rollDice(currentState: GameState, token: String): GameState
    suspend fun endTurnAndSimulate(currentState: GameState, token: String): GameState
    suspend fun startNextRound(currentState: GameState): GameState
}

class GameRemoteServiceImpl(
    private val client: HttpClient
) : GameService {

    override suspend fun getInitialGameState(lobbyId: Int, token: String): GameState {
        Log.d("GAME_SERVICE", "1. A pedir dados do JOGO...")
        val gameDto: GameDto = client.get("$BASE_URL/games/lobby/$lobbyId") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()

        Log.d("GAME_SERVICE", "2. A pedir lista de JOGADORES...")
        val playersResponse: LobbyPlayersResponseDto =
            client.get("$BASE_URL/users/obj/lobby/$lobbyId") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()

        Log.d("GAME_SERVICE", "3. A combinar tudo...")
        return gameDto.toGameState(playersResponse.players)
    }

    override suspend fun rollDice(currentState: GameState, token: String): GameState {
        /*
        val gameId = currentState.id  // se precisares, inclui id no GameState
        val dto: GameDto = client.post("$BASE_URL/games/$gameId/roll") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
        return dto.toGameState()*/
        TODO()
    }

    override suspend fun endTurnAndSimulate(currentState: GameState, token: String): GameState {
        /*
        val gameId = currentState.id
        val dto: GameDto = client.post("$BASE_URL/games/$gameId/end-turn") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
        return dto.toGameState()
        */
        TODO()
    }

    override suspend fun startNextRound(currentState: GameState): GameState {
        /*
        // se o backend tiver endpoint de próxima ronda, chama-o aqui;
        // senão, podes fazer só um GET ao estado atual
        val gameId = currentState.id
        val dto: GameDto = client.get("$BASE_URL/games/$gameId").body()
        return dto.toGameState()
    }
    */
        TODO()
    }
}

/*
class GameFakeServiceImpl : GameService {

    private val db = FakeDatabase
    private val logic = GameLogic

    override suspend fun getInitialGameState(lobbyId: Int, token: String): GameState {
        delay(500)
        val userId = tokens.find { it.tokenValidation == token }?.userId
            ?: throw IllegalStateException("Token inválido")
        val me = db.usersFlow.value.find { it.id == userId }
            ?: throw IllegalStateException("User não encontrado")
        val allPlayersInLobby = db.usersFlow.value.filter { it.lobbyId == lobbyId }

        val playerStatuses = allPlayersInLobby.map { user ->
            PlayerStatus(
                id = user.id,
                name = user.username,
                dice = null,
                hand = null,
                isCurrentTurn = (user.id == me.id)
            )
        }

        return GameState(
            dice = rollNewDice(), players = playerStatuses, currentPlayerName = me.username,
            rollsLeft = 2, roundNumber = 1, canRoll = true
        )
    }

    override suspend fun rollDice(currentState: GameState, token: String): GameState {
        delay(300)
        if (!currentState.canRoll) return currentState

        val newDice =
            currentState.dice.map { if (it.isHeld) it else it.copy(face = DiceFace.random()) }
        val newRollsLeft = currentState.rollsLeft - 1
        return currentState.copy(
            dice = newDice,
            rollsLeft = newRollsLeft,
            canRoll = newRollsLeft > 0
        )
    }

    override suspend fun endTurnAndSimulate(currentState: GameState, token: String): GameState {
        delay(500)
        val myFinalDice = currentState.dice
        val myFinalHand = logic.calculateHandScore(myFinalDice.map { it.face })
        val currentPlayerId = currentState.players.find { it.isCurrentTurn }!!.id

        val playersWithCurrentHand = currentState.players.map {
            if (it.id == currentPlayerId) {
                it.copy(dice = myFinalDice, hand = myFinalHand, isCurrentTurn = false)
            } else {
                it
            }
        }

        val nextPlayer = playersWithCurrentHand.find { it.dice == null }

        if (nextPlayer != null) {

            val playersWithNextTurn = playersWithCurrentHand.map {
                if (it.id == nextPlayer.id) {
                    it.copy(isCurrentTurn = true)
                } else {
                    it
                }
            }

            return GameState(
                dice = rollNewDice(),
                players = playersWithNextTurn,
                currentPlayerName = nextPlayer.name,
                rollsLeft = 2,
                roundNumber = currentState.roundNumber,
                canRoll = true,
                roundWinners = currentState.roundWinners
            )
        } else {

            val winner = playersWithCurrentHand.maxBy { it.hand!!.score }

            val updatedHistory = currentState.roundWinners + winner

            return currentState.copy(
                players = playersWithCurrentHand,
                canRoll = false,
                rollsLeft = 0,
                roundWinners = updatedHistory,
                finalWinners = emptyList()
            )
        }

    }

    override suspend fun startNextRound(currentState: GameState): GameState {
        delay(1000)
        val nextRoundNumber = currentState.roundNumber + 1

        if (nextRoundNumber > currentState.players.size) {
            val gameWinners = currentState.roundWinners
                .groupBy { it.id }
                .mapValues { it.value.size }
                .entries
                .maxByOrNull { it.value }
                ?.let { maxEntry ->
                    currentState.players.filter { player ->
                        currentState.roundWinners.count { it.id == player.id } == maxEntry.value
                    }
                } ?: emptyList()


            return currentState.copy(finalWinners = gameWinners)
        }

        val nextPlayerIndex =
            (nextRoundNumber - 1) % currentState.players.size // TODO MUDAR PARA RANDOM ?
        val nextPlayerId = currentState.players[nextPlayerIndex].id

        val nextRoundPlayers = currentState.players.map {
            it.copy(dice = null, hand = null, isCurrentTurn = (it.id == nextPlayerId))
        }

        val nextPlayerName = nextRoundPlayers.find { it.isCurrentTurn }!!.name

        return GameState(
            dice = rollNewDice(), players = nextRoundPlayers, currentPlayerName = nextPlayerName,
            rollsLeft = 2, roundNumber = nextRoundNumber, canRoll = true,
            roundWinners = currentState.roundWinners
        )
    }

    private fun rollNewDice(): List<Die> {
        return List(5) { index -> Die(id = index, face = DiceFace.random(), isHeld = false) }
    }

}

 */