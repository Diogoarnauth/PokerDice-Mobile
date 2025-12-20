package com.example.chelasmultiplayerpokerdice.auth.signup

import android.util.Log
import com.example.chelasmultiplayerpokerdice.BASE_URL
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.domain.remote.models.*
import com.example.chelasmultiplayerpokerdice.ui.theme.common.ErrorAlert
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText

interface SignupService {

    suspend fun needsBootstrap(): Boolean

    suspend fun signup(
        username: String,
        password: String,
        name: String,
        age: Int,
        inviteCode: String
    ): AuthenticatedUser?
}

private const val TAG = "SIGNUP_SERVICE"

class SignupServiceImpl(
    private val client: HttpClient
) : SignupService {

    override suspend fun needsBootstrap(): Boolean {
        return try {
            val url = "$BASE_URL/checkAdmin"
            Log.d("TAG", "Verificando necessidade de Bootstrap em: $url")
            val response = client.get(url)
            Log.d(" TAG", "Resposta do servidor: ${response.status.value} - ${response.bodyAsText()}")

            if (response.status.value == 200) {
                val res: CheckAdminResponseDto = response.body()
                res.firstUser
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signup(
        username: String,
        password: String,
        name: String,
        age: Int,
        inviteCode: String
    ): AuthenticatedUser? {
        try {

            val bootstrapUrl = "$BASE_URL/bootstrap"
            val signupUrl = "$BASE_URL/users"
            val loginUrl = "$BASE_URL/users/token"

            val needsBootstrap = needsBootstrap()

            val registerResponse = if (needsBootstrap) {
                client.post(bootstrapUrl) {
                    header("Content-Type", "application/json") // Header explícito
                    setBody(BootstrapRequestDto(username, name, age, password))
                }
            } else {
                val finalInvite = if (inviteCode.isBlank()) "SEM_CODIGO" else inviteCode
                client.post(signupUrl) {
                    header("Content-Type", "application/json")
                    setBody(SignupRequestDto(username, name, age, password, finalInvite))
                }
            }

            if (registerResponse.status.value !in 200..299) {
                Log.e(TAG, "Falha no Registo: ${registerResponse.bodyAsText()}")
                //throw ErrorAlert() { }//SignupException("Falha no Registo: ${registerResponse.bodyAsText()}")
            }

            val loginResponse = client.post(loginUrl) {
                header("Content-Type", "application/json")
                setBody(LoginRequestDto(username, password))
            }

            if (loginResponse.status.value != 200) {
                Log.e(TAG, "Registo OK, mas falha no Login: ${loginResponse.bodyAsText()}")
                //}
                //throw SignupException("Registo OK, Login falhou: ${loginResponse.bodyAsText()}")
            }

            val loginBody = loginResponse.body<LoginResponseDto>()
            return AuthenticatedUser(username, loginBody.token)

        } catch (e: Exception) {
            Log.e("SignupService", "Erro: ${e.message}")
            e.printStackTrace()
            return null
        }
    }
}