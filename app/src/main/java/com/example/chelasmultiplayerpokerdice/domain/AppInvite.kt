package com.example.chelasmultiplayerpokerdice.domain

data class AppInvite(
    val id: Int,
    val inviterId: Int,
    val inviteValidationInfo: String,
    val state: String,
    val createdAt: Long,
)
