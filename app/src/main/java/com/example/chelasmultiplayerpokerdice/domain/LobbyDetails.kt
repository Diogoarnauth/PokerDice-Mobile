package com.example.chelasmultiplayerpokerdice.domain

data class LobbyDetails(
    val lobby: Lobby,
    val players: List<User>
)