package com.example.chelasmultiplayerpokerdice.domain

data class User(
    val id: Int,
    val username: String,
    val passwordValidation: String,
    val name: String,
    val age: Int,
    var credit: Int,
    var winCounter: Int,
    var lobbyId: Int? = null,
)
