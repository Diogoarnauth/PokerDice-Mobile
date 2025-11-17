package com.example.chelasmultiplayerpokerdice.domain

data class Game(
    val id: Int,
    val lobbyId: Int,
    var state: GameStatus,
    var nrUsers: Int,
    var roundCounter: Int,
)

enum class GameStatus { CLOSED, RUNNING }