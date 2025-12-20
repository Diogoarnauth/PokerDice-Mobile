package com.example.chelasmultiplayerpokerdice.game

import com.example.chelasmultiplayerpokerdice.domain.Die

data class PlayerHand(
    val name: String,
    val score: Double
) : Comparable<PlayerHand> {
    override fun compareTo(other: PlayerHand): Int {
        return this.score.compareTo(other.score)
    }
}

data class PlayerStatus(
    val id: Int,
    val name: String,
    val dice: List<Die>?,
    val hand: PlayerHand?,
    val isCurrentTurn: Boolean
)

data class GameState(
    val id: Int,
    val dice: List<Die>,
    val players: List<PlayerStatus>,
    val currentPlayerName: String,
    val rollsLeft: Int,
    val roundWinners: List<PlayerStatus> = emptyList(),
    val finalWinners: List<PlayerStatus> = emptyList(),
    val roundNumber: Int,
    val canRoll: Boolean
)

interface GameScreenState {
    data object Loading : GameScreenState
    data class Playing(val gameState: GameState) : GameScreenState

    data class GameOver(val gameState: GameState, val winners: List<PlayerStatus>) : GameScreenState
    data class RoundOver(val gameState: GameState, val winner: PlayerStatus) : GameScreenState

    data class Error(val message: String) : GameScreenState
}