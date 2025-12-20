@file:OptIn(kotlinx.serialization.InternalSerializationApi::class, kotlinx.serialization.ExperimentalSerializationApi::class)
package com.example.chelasmultiplayerpokerdice.domain.remote.models

import com.example.chelasmultiplayerpokerdice.domain.DiceFace
import com.example.chelasmultiplayerpokerdice.domain.Die
import com.example.chelasmultiplayerpokerdice.game.GameState
import com.example.chelasmultiplayerpokerdice.game.PlayerHand
import com.example.chelasmultiplayerpokerdice.game.PlayerStatus
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: Int,
    val lobbyId: Int,
    val state: String,
    val roundsCounter: Int? = null,
    val nrUsers: Int? = null,
)

@Serializable
data class DieDto(
    val id: Int,
    val face: String,      // ou Int, consoante o JSON
    val held: Boolean
)

@Serializable
data class PlayerStatusDto(
    val id: Int,
    val username: String,
    val dice: List<DieDto>?,
    val score: Double?,
    val isCurrentTurn: Boolean
)

// DTO -> domínio (GameState usado pela UI)
fun GameDto.toGameState(): GameState =
    GameState(
        id = id,
        dice = emptyList(),
        players = emptyList(),
        currentPlayerName = "",
        rollsLeft = 2,
        roundNumber = roundsCounter ?: 1,
        canRoll = true,
        roundWinners = emptyList(),
        finalWinners = emptyList()
    )




fun DieDto.toDie() = Die(
    id = id,
    face = DiceFace.fromLabel(face),   // cria helper se ainda não existir
    isHeld = held
)

fun PlayerStatusDto.toPlayerStatus() = PlayerStatus(
    id = id,
    name = username,
    dice = dice?.map { it.toDie() },
    hand = score?.let { PlayerHand(username, it) },
    isCurrentTurn = isCurrentTurn
)
