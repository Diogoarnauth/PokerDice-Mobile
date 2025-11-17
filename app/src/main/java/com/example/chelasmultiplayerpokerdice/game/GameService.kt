package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import com.example.chelasmultiplayerpokerdice.domain.DiceFace
import com.example.chelasmultiplayerpokerdice.domain.Die
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase.tokens
import kotlinx.coroutines.delay
interface GameService {
    suspend fun getInitialGameState(lobbyId: Int, token: String): GameState
    suspend fun rollDice(currentState: GameState, token: String): GameState
    suspend fun endTurnAndSimulate(currentState: GameState, token: String): GameState
    suspend fun startNextRound(currentState: GameState): GameState
}

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