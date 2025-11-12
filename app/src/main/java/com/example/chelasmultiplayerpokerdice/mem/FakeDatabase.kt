package com.example.chelasmultiplayerpokerdice.mem

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Clock
object FakeDatabase {

    var tokens = mutableListOf<Token>()
    val users = mutableListOf(
        User(
            id = 1,
            username = "renata",
            passwordValidation = "pass123",
            name = "Renata Castanheira",
            age = 19,
            credit = 100,
            winCounter = 0,
            lobbyId = 1
        ),
        User(
            id = 2,
            username = "diogo",
            passwordValidation = "pass123",
            name = "Diogo Arnauth",
            age = 20,
            credit = 100,
            winCounter = 0,
            lobbyId = 1
        ),
        User(
            id = 3,
            username = "beto",
            passwordValidation = "1234",
            name = "Humberto Carvalho",
            age = 21,
            credit = 100,
            winCounter = 0,
            lobbyId = null
        )

    )
    var nextUserId = users.size

    private val initialLobbies = listOf(
        Lobby(
            id = 1,
            name = "Poker Masters",
            description = "Lobby para testar a sorte 🎲",
            hostId = 1,
            minUsers = 2,
            maxUsers = 4,
            rounds = 12,
            minCreditToParticipate = 1,
            isRunning = false,
            playersCount = 2,
            users = users.filter { it.lobbyId == 1 }
        )
    )
    private var nextLobbyId = 2 //TODO() numeros magicos

    private val _lobbies = MutableStateFlow(initialLobbies)
    val lobbies = _lobbies.asStateFlow()


    fun createLobby(
        name: String,
        description: String,
        hostId: Int,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCredit: Int
    ): Lobby {
        val hostUser = users.find { it.id == hostId }
            ?: throw IllegalArgumentException("Utilizador 'host' não encontrado")
        val newLobby = Lobby(
            id = ++nextLobbyId,
            name = name,
            description = description,
            hostId = hostId,
            minUsers = minUsers,
            maxUsers = maxUsers,
            rounds = rounds,
            minCreditToParticipate = minCredit,
            isRunning = false,
            playersCount = 1,
            users = listOf(hostUser)
        )

        _lobbies.update { currentList -> currentList + newLobby }
        return newLobby
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(username: String, password: String): Token? {
        Log.d("login estranho", "Token: $username e $password")

        val user = users.find { it.username == username && it.passwordValidation == password }
        Log.d(" beto apanhei-te??", "Token: $user")

        if (user != null) {
            val tokenString = "token-for-${user.username}-${Clock.systemUTC().millis()}"
            val newToken =
                Token(tokenString,
                    Clock.systemUTC().millis(),
                    Clock.systemUTC().millis(),
                    user.id)
            tokens.removeAll { it.userId == user.id }
            tokens.add(newToken)
            return newToken
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O) // TODO ?? TIRAR ISTo
    fun signup(username: String, password: String, name: String, age: Int): Token? {
        println("dentro da fun do sihnUp")
        if (users.any { it.username == username }) {
            return null
        }

        val newUser = User(
            id = ++nextUserId,
            username = username,
            passwordValidation = password,
            name = name,
            age = age,
            credit = 100,
            winCounter = 0,
            lobbyId = null
        )
        println("criou user $newUser")
        users.add(newUser)
        println("users $users")
        return login(username, password)
    }

    fun joinLobby(lobbyId: Int, userId: Int) {
        val userToJoin = users.find { it.id == userId } ?: return

        _lobbies.update { currentList ->
            currentList.map { lobby ->
                if (lobby.id == lobbyId) {

                    if (lobby.users.size >= lobby.maxUsers) {
                        lobby
                    }
                    else if (lobby.users.any { it.id == userId }) {
                        lobby
                    }
                    else {
                        val updatedPlayers = lobby.users + userToJoin
                        lobby.copy(
                            users = updatedPlayers,
                            playersCount = updatedPlayers.size
                        )
                    }
                } else {
                    lobby
                }
            }
        }
    }

    fun abandonLobby(lobbyId: Int, userId: Int) {
        _lobbies.update { currentList ->

            val lobbyToUpdate = currentList.find { it.id == lobbyId }
            if (lobbyToUpdate == null) return@update currentList

            val updatedPlayers =
                lobbyToUpdate.users.filterNot { it.id == userId }

            if (updatedPlayers.isEmpty()) {
                currentList.filterNot { it.id == lobbyId }
            } else {
                currentList.map {
                    if (it.id == lobbyId) {
                        it.copy(
                            users = updatedPlayers,
                            playersCount = updatedPlayers.size
                        )
                    } else {
                        it
                    }
                }
            }
        }
    }
}