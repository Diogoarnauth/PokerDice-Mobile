package com.example.chelasmultiplayerpokerdice.mem

data class AppInvite(
    val id: Int,
    val inviterId: Int,
    val inviteValidationInfo: String,
    val state: String,
    val createdAt: Long,
)
