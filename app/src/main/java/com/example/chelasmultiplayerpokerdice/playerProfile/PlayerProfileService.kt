package com.example.chelasmultiplayerpokerdice.playerProfile

import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase.tokens

interface PlayerProfileService {
    fun getPlayerProfileData(token: String?): User
}

class PlayerProfileFakeServiceImpl : PlayerProfileService {

    private val db = FakeDatabase

    override fun getPlayerProfileData(token: String?): User {

        if (token == null) {
            throw IllegalStateException("Token é nulo. O utilizador não está logado.")
        }

        val userToken = tokens.find { it.tokenValidation == token }
            ?: throw IllegalStateException("Token inválido ou sessão expirada")

        val user = db.users.find { it.id == userToken.userId }
            ?: throw IllegalStateException("Utilizador não encontrado na DB")

        return user
    }
}
