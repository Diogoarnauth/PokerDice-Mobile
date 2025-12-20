package com.example.chelasmultiplayerpokerdice.domain

data class Lobby(
    val id: Int,
    val name: String,
    val description: String,
    val hostId: Int,
    val minUsers: Int,
    val maxUsers: Int,
    val rounds: Int,
    val minCreditToParticipate: Int,
)