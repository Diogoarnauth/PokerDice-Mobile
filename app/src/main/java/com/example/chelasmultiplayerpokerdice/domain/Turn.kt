package com.example.chelasmultiplayerpokerdice.domain

data class Turn(
    val id: Int,
    val roundId: Int,
    val playerId: Int,
    var rollCount: Int = 0,
    var diceFaces: List<DiceFace> = emptyList(),
    var value_of_combination: Int = 0,
    var isDone: Boolean = false,
)