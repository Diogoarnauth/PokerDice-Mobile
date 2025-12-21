/*@file:OptIn(kotlinx.serialization.InternalSerializationApi::class, kotlinx.serialization.ExperimentalSerializationApi::class)
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
    val roundCounter: Int,
    val nrUsers: Int,
    val players: List<PlayerStatusDto> = emptyList(),
    val currentPlayerId: Int? = null
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
        roundNumber = roundCounter,
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
*/
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
    val state: String,       // Ex: "ONGOING", "FINISHED"
    val roundCounter: Int,   // ✅ CORRIGIDO: Era 'roundsCounter'
    val nrUsers: Int,
    // ✅ NOVO: Temos de receber os jogadores, senão o jogo não funciona!
    // Se o backend não enviar isto, temos de falar, mas geralmente envia.
    val players: List<PlayerStatusDto> = emptyList(),
    // Opcional: Se o backend enviar de quem é a vez pelo ID
    val currentPlayerId: Int? = null
)

@Serializable
data class DieDto(
    val id: Int,
    val face: String, // O backend deve mandar "ACE", "KING", etc.
    val held: Boolean
)

@Serializable
data class PlayerStatusDto(
    val id: Int,
    val username: String,
    val dice: List<DieDto>? = null, // Pode vir null se o jogador ainda não jogou
    val score: Double? = null,      // Pode vir null
    val isCurrentTurn: Boolean
)

// --- MAPPERS (A Conversão Mágica) ---

fun GameDto.toGameState(lobbyPlayers: List<PlayerDto>): GameState {

    // Convertemos os Users do Lobby em PlayerStatus do Jogo
    val domainPlayers = lobbyPlayers.map { user ->
        PlayerStatus(
            id = user.id,
            name = user.username, // ou user.name
            dice = null, // Inicialmente sem dados
            hand = null, // Inicialmente sem pontuação
            isCurrentTurn = false // Vamos calcular isto abaixo
        )
    }

    // Lógica para decidir quem começa (isto devia vir do backend, mas improvisamos)
    // Se currentPlayerId vier a null, assume o primeiro da lista
    val currentPlayerIdSafe = currentPlayerId ?: domainPlayers.firstOrNull()?.id ?: -1

    // Atualiza a lista para marcar quem é a vez
    val playersWithTurn = domainPlayers.map {
        if(it.id == currentPlayerIdSafe) it.copy(isCurrentTurn = true) else it
    }

    val currentPlayer = playersWithTurn.find { it.isCurrentTurn }
    val currentPlayerName = currentPlayer?.name ?: "A carregar..."

    // Se o jogador atual não tiver dados, tem 3 rolagens. Se tiver, tem 2 (lógica simples)
    val rollsLeft = 3

    return GameState(
        id = id,
        dice = emptyList(), // O jogo começa sem dados na mesa
        players = playersWithTurn, // ✅ AGORA JÁ TEMOS JOGADORES!
        currentPlayerName = currentPlayerName,
        rollsLeft = rollsLeft,
        roundNumber = roundCounter,
        canRoll = true,
        roundWinners = emptyList(),
        finalWinners = emptyList()
    )
}

fun DieDto.toDie() = Die(
    id = id,
    face = DiceFace.fromLabel(face),   // cria helper se ainda não existir
    isHeld = held
)

fun PlayerStatusDto.toPlayerStatus() = PlayerStatus(
    id = id,
    name = username,
    dice = dice?.map { it.toDie() }, // Converte os dados se existirem
    hand = score?.let { PlayerHand(username, it) },
    isCurrentTurn = isCurrentTurn
)