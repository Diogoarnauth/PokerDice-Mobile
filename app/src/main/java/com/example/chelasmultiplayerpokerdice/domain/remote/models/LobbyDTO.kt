@file:OptIn(kotlinx.serialization.InternalSerializationApi::class, kotlinx.serialization.ExperimentalSerializationApi::class)
package com.example.chelasmultiplayerpokerdice.domain.remote.models

import kotlinx.serialization.Serializable

// Input para criar lobby
@Serializable
data class LobbyCreateRequestDto(
    val name: String,
    val description: String,
    val minUsers: Int,
    val maxUsers: Int,
    val rounds: Int,
    val minCreditToParticipate: Int,
    val turnTime: Int
)

// Output ao listar lobbies (GET /lobbies) e detalhes (GET /lobbies/{id})
@Serializable
data class LobbyDto(
    val id: Int,
    val name: String,
    val description: String,
    val hostId: Int,
    val minUsers: Int,
    val maxUsers: Int,
    val rounds: Int,
    val minCreditToParticipate: Int,
    val isRunning: Boolean,
    val turnTime: String
)

// Output da lista de jogadores (GET /users/obj/lobby/{id})
@Serializable
data class LobbyPlayersResponseDto(
    val lobbyId: Int,
    val count: Int,
    val players: List<PlayerDto>
)
@Serializable
data class PlayerDto(
    val id: Int,
    val username: String,
    val name: String,
    val age: Int,
    val credit: Int,
    val winCounter: Int,
    val lobbyId: Int?
)