package com.example.chelasmultiplayerpokerdice.domain

class Round (
    val id: Int,
    val gameId: Int,
    var roundNumber: Int,
    var isRoundOver: Boolean,
)