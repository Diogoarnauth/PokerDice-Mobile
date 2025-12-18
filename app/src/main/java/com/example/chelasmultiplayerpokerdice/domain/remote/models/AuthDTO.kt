@file:OptIn(kotlinx.serialization.InternalSerializationApi::class, kotlinx.serialization.ExperimentalSerializationApi::class)
package com.example.chelasmultiplayerpokerdice.domain.remote.models
import kotlinx.serialization.Serializable

@Serializable
data class BootstrapRequestDto(
    val username: String,
    val name: String,
    val age: Int,
    val password: String
)

// Resposta do endpoint /checkAdmin
@Serializable
data class CheckAdminResponseDto(
    val firstUser: Boolean
)
@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String
)
@Serializable
data class LoginResponseDto(
    val token: String
)
@Serializable
data class SignupRequestDto(
    val username: String,
    val name: String,
    val age: Int,
    val password: String,
    val inviteCode: String = "" // O backend pede, enviamos vazio ou valor fixo
)

// O teu backend retorna isto no POST /users (create)
@Serializable
data class SignupResponseDto(
    val id: Int,
    val location: String
)