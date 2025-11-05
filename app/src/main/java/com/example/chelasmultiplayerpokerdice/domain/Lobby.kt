package com.example.chelasmultiplayerpokerdice.domain

data class Lobby(
    val id: Int,
    val name: String,
    val description: String,
    val hostId: Int, // TODO( MUDAR PARA INT INT)
    val minUsers: Int,
    val maxUsers: Int,
    val rounds: Int,
    val minCreditToParticipate: Int,
    var isRunning: Boolean = false,
    val playersCount: Int, // daqui para baixo ESTÁ DIFERENTE DE DAW
    val players: List<Player>
)

// package pt.isel.daw.pokerDice.domain.lobbies
//
//// Lobby interno
//data class Lobby(
//    val id: Int,
//    val name: String,
//    val description: String,
//    val hostId: Int,
//    val minUsers: Int,
//    val maxUsers: Int,
//    val rounds: Int,
//    val minCreditToParticipate: Int,
//    var isRunning: Boolean = false,
//)