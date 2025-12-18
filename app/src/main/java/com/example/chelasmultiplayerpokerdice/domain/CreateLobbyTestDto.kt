package com.example.chelasmultiplayerpokerdice.domain

data class CreateLobbyTestDto(
    val name: String,
    val description: String,
    val minUsers: Int,
    val maxUsers: Int,
    val rounds: Int,
    val minCreditToParticipate: Int,
    val turnTime: Int // Backend espera Int (minutos)
)