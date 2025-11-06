package com.example.chelasmultiplayerpokerdice.mem

import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A nossa "base de dados" fake em memória, tal como no projeto 'chimp'.
 * Por ser um 'object', é um Singleton (só há um).
 *
 * Usamos um MutableStateFlow para que qualquer mudança na lista de lobbies
 * seja "emitida" automaticamente. Isto cumpre o requisito de auto-refresh!
 * [cite: 85, 97, 101, 106]
 */
object FakeDatabase {

    // Placeholder para o jogador "logado" (Milestone 3)
    val myUser = Player(1, "Renata (Eu)")

    // Lista de Lobbies inicial (com o teu modelo de dados)
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
            playersCount = 3,
            players = listOf(myUser, Player(2, "Diogo"), Player(3, "Humberto"))
        ),
        Lobby(
            id = 2,
            name = "Chelas Crew",
            description = "Só para pros",
            hostId = 2,
            minUsers = 2,
            maxUsers = 2,
            rounds = 10,
            minCreditToParticipate = 5,
            isRunning = false,
            playersCount = 1,
            players = listOf(Player(2, "Diogo"))
        )
    )

    private var nextLobbyId = 3

    // O "coração" da base de dados.
    private val _lobbies = MutableStateFlow(initialLobbies)
    val lobbies = _lobbies.asStateFlow() // Versão 'read-only' para os outros lerem

    // --- Funções que os Serviços vão chamar ---

    fun createLobby(
        name: String,
        description: String,
        hostId: Int,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCredit: Int
    ): Lobby {
        val newLobby = Lobby(
            id = nextLobbyId++,
            name = name,
            description = description,
            hostId = hostId,
            minUsers = minUsers,
            maxUsers = maxUsers,
            rounds = rounds,
            minCreditToParticipate = minCredit,
            isRunning = false,
            playersCount = 1, // Começa com 1 jogador (o host)
            players = listOf(myUser) // O host entra no lobby
        )

        // .update() é a magia. Altera a lista e notifica todos
        // os ViewModels que estiverem a "ouvir" (collect) o Flow.
        _lobbies.update { currentList -> currentList + newLobby }
        return newLobby
    }

    fun getLobbyById(id: Int): Lobby? {
        return _lobbies.value.find { it.id == id }
    }

    fun abandonLobby(lobbyId: Int) {
        _lobbies.update { currentList ->
            currentList.map { lobby ->
                if (lobby.id == lobbyId) {
                    val updatedPlayers = lobby.players.filter { it.id != myUser.id }
                    lobby.copy(
                        players = updatedPlayers,
                        playersCount = updatedPlayers.size
                    )
                } else {
                    lobby
                }
            }
        }
    }
}