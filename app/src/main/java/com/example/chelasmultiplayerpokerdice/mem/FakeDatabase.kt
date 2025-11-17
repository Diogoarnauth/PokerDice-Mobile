package com.example.chelasmultiplayerpokerdice.mem

import android.util.Log
import com.example.chelasmultiplayerpokerdice.domain.Game
import com.example.chelasmultiplayerpokerdice.domain.GameStatus
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.Round
import com.example.chelasmultiplayerpokerdice.domain.Token
import com.example.chelasmultiplayerpokerdice.domain.Turn
import com.example.chelasmultiplayerpokerdice.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// --- Modelos de Dados da Base de Dados Fake ---

/**
 * A nossa "base de dados" fake em memória, estilo 'chimp'.
 * É um Singleton (object) para ser partilhado por todos os serviços.
 * Utiliza StateFlow para que as alterações sejam reativas (auto-refresh).
 */
object FakeDatabase {

    var tokens = mutableListOf<Token>()

    // --- USERS ---
    private val initialUsers = mutableListOf(
        User(
            id = 1,
            username = "renata",
            passwordValidation = "pass123",
            name = "Renata Castanheira",
            age = 19,
            credit = 100,
            winCounter = 0,
            lobbyId = 1 // renata está no lobby 1
        ),
        User(
            id = 2,
            username = "diogo",
            passwordValidation = "pass123",
            name = "Diogo Arnauth",
            age = 20,
            credit = 100,
            winCounter = 0,
            lobbyId = 1 // diogo está no lobby 1
        ),
        User(
            id = 3,
            username = "beto",
            passwordValidation = "1234",
            name = "Humberto Carvalho",
            age = 21,
            credit = 100,
            winCounter = 0,
            lobbyId = null // beto não está em nenhum lobby
        )
    )
    private val _users = MutableStateFlow(initialUsers)
    val usersFlow: StateFlow<List<User>> = _users.asStateFlow() // Flow de Users
    var nextUserId = 4

    // --- LOBBIES ---
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
            isRunning = false
        )
    )
    private val _lobbies = MutableStateFlow(initialLobbies)
    val lobbies: StateFlow<List<Lobby>> = _lobbies.asStateFlow() // Flow de Lobbies
    private var nextLobbyId = 2

    // --- GAME ---
    val games = mutableListOf<Game>()
    val rounds = mutableListOf<Round>()
    val turns = mutableListOf<Turn>()
    var nextGameId = 1
    var nextRoundId = 1
    var nextTurnId = 1

    // ===================================
    // --- Funções da Base de Dados ---
    // ===================================

    fun getLobbyById(id: Int): Lobby? {
        return _lobbies.value.find { it.id == id }
    }

    /**
     * Simula o início do jogo.
     */
    fun createGame(lobby: Lobby, players: List<User>): Game {
        val newGame = Game(
            id = nextGameId++,
            lobbyId = lobby.id,
            state = GameStatus.RUNNING,
            nrUsers = players.size,
            roundCounter = 1
        )
        games.add(newGame)

        val newRound = Round(
            id = nextRoundId++,
            gameId = newGame.id,
            roundNumber = 1,
            isRoundOver = false
        )
        rounds.add(newRound)

        players.forEach { player ->
            val newTurn = Turn(
                id = nextTurnId++,
                roundId = newRound.id,
                playerId = player.id,
                isDone = false
            )
            turns.add(newTurn)
        }
        return newGame
    }

    /**
     * Cria um lobby e define o 'lobbyId' do anfitrião.
     */
    fun createLobby(
        name: String,
        description: String,
        hostId: Int,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCredit: Int
    ): Lobby {
        val hostUser = _users.value.find { it.id == hostId }
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
            isRunning = false
        )

        // Adiciona o lobby à lista de lobbies (dispara o Flow _lobbies)
        _lobbies.update { currentList -> currentList + newLobby }

        // Atualiza o 'lobbyId' do user anfitrião (dispara o Flow _users)
        _users.update { currentUsers ->
            currentUsers.map {
                if (it.id == hostId) it.copy(lobbyId = newLobby.id) else it
            }.toMutableList()
        }

        return newLobby
    }

    /**
     * Autentica um user e devolve um token.
     * Corrigido para usar System.currentTimeMillis().
     */
    fun login(username: String, password: String): Token? {

        val user = _users.value.find { it.username == username && it.passwordValidation == password }

        if (user != null) {
            val now = System.currentTimeMillis() // <-- CORRIGIDO
            val tokenString = "token-for-${user.username}-$now"
            val newToken = Token(tokenString, now, now, user.id)

            tokens.removeAll { it.userId == user.id }
            tokens.add(newToken)
            return newToken
        }
        return null
    }

    /**
     * Regista um novo user e faz login automaticamente.
     * Corrigido para usar System.currentTimeMillis().
     */
    fun signup(username: String, password: String, name: String, age: Int): Token? {
        println("dentro da fun do sihnUp")
        if (_users.value.any { it.username == username }) {
            return null // Username já existe
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
        // Atualiza a lista de users (dispara o Flow _users)
        _users.update { (it + newUser).toMutableList() }
        println("users ${_users.value}")
        return login(username, password)
    }

    /**
     * Altera o 'lobbyId' do user para o fazer "entrar" num lobby.
     */
    fun joinLobby(lobbyId: Int, userId: Int) {
        val lobby = _lobbies.value.find { it.id == lobbyId } ?: return
        val playersInLobby = _users.value.count { it.lobbyId == lobbyId }

        if (playersInLobby >= lobby.maxUsers) {
            return // Lobby está cheio
        }

        // Atualiza o user (dispara o Flow _users)
        _users.update { currentList ->
            currentList.map {
                if (it.id == userId && it.lobbyId != lobbyId) {
                    it.copy(lobbyId = lobbyId)
                } else {
                    it
                }
            }.toMutableList()
        }
    }

    /**
     * Altera o 'lobbyId' do user para 'null'
     * e apaga o lobby se for o último a sair.
     */
    fun abandonLobby(lobbyId: Int, userId: Int) {
        // 1. Tira o user do lobby (dispara o Flow _users)
        _users.update { currentList ->
            currentList.map {
                if (it.id == userId) it.copy(lobbyId = null) else it
            }.toMutableList()
        }

        // 2. Vê se o lobby ficou vazio (lendo o estado ATUAL do Flow)
        val playersLeft = _users.value.count { it.lobbyId == lobbyId }

        if (playersLeft == 0) {
            // 3. Apaga o lobby (dispara o Flow _lobbies)
            _lobbies.update { currentList ->
                currentList.filterNot { it.id == lobbyId }
            }
        }
    }
}