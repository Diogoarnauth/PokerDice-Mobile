package com.example.chelasmultiplayerpokerdice.mem

data class Token (
    var tokenValidation:String? = null,
    val createdAt: Long,
    val lastUsedAt: Long,
    val userId: Int,
)