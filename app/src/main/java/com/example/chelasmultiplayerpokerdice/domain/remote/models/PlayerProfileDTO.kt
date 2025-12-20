@file:OptIn(kotlinx.serialization.InternalSerializationApi::class, kotlinx.serialization.ExperimentalSerializationApi::class)
package com.example.chelasmultiplayerpokerdice.domain.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerProfileResponseDto(
    val id: Int = 0,
    val username: String = "",
    val name: String = "",
    val age: Int = 0,
    val credit: Int = 0,
    val winCounter: Int = 0,
    val lobbyId: Int? = null
)

@Serializable
data class DepositRequest(val value: Int)

@Serializable
data class InviteAppOutputModel(
    @SerialName("inviteCode")
    val inviteCode: String
)