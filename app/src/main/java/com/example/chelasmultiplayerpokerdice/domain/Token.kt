package com.example.chelasmultiplayerpokerdice.domain

data class Token (
    var tokenValidation:String,
    val createdAt: Long,
    val lastUsedAt: Long,
    val userId: Int,
)