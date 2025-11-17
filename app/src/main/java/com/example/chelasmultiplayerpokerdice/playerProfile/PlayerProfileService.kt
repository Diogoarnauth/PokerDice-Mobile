package com.example.chelasmultiplayerpokerdice.playerProfile

import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase.tokens
import kotlinx.coroutines.delay

interface PlayerProfileService {
   suspend fun getPlayerProfileData(token: String): User
}

class PlayerProfileFakeServiceImpl : PlayerProfileService {

    private val db = FakeDatabase

    override suspend fun getPlayerProfileData(token: String): User {

        delay(500)

        val userToken = tokens.find { it.tokenValidation == token }
            ?: throw IllegalStateException("Token inválido ou sessão expirada")

        val user = db.usersFlow.value.find { it.id == userToken.userId }
            ?: throw IllegalStateException("Utilizador não encontrado na DB")


        return user
    }
}
