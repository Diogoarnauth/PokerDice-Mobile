package com.example.chelasmultiplayerpokerdice.playerProfile

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
            Log.d("PokerDice", "Token nulo recebido!")
            throw IllegalStateException("Token é nulo. O utilizador não está logado.")
        }

        val userToken = tokens.find { it.tokenValidation == token }
            ?: throw IllegalStateException("Token inválido ou sessão expirada")

        Log.d("banana", "Token: $token")
        val user = db.users.find { it.id == userToken.userId }
            ?: throw IllegalStateException("Utilizador não encontrado na DB")
        Log.d("pera", "Token: $user")


        return user
    }
}
