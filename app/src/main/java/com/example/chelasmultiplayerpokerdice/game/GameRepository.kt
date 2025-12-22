package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.Game
import com.example.chelasmultiplayerpokerdice.domain.remote.models.toDie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GameRepository(private val service: GameService) {

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState.asStateFlow()

    private val mutex = Mutex()

    /**
     * Inicia o jogo (Vai ao serviço buscar o estado inicial e emite no Flow)
     */


    fun getGameLive(lobbyId: Int, token: String): Flow<GameState> = flow {
        while (true) {
            try {
                val gameState = service.fetchFullGameState(_gameState.value!!, lobbyId, token)
                Log.d(TAG, "<CICLO> GAMESTATE $gameState")
                _gameState.value = gameState
                emit(gameState)
                Log.d(TAG, "CICLOOOOOOO")
            } catch (e: Exception) { Log.e(TAG, "Erro no Polling: ${e.message}") }
            delay(2000)
        }
    }

    suspend fun checkGameStarted(lobbyId: Int, token: String): Int? {
        val gameDto = service.getGameByLobby(lobbyId, token)
        return gameDto?.id
    }


    suspend fun fetchGame(lobbyId: Int, token: String) {
        Log.d(TAG, "ENTREI NO fetchGame")
        //mutex.withLock {
            try {
                val initialState = service.getInitialGameState(lobbyId, token)
                _gameState.value = initialState
                Log.d(TAG, "fetchGame SUCESSO! Estado atualizado.")
            } catch (e: Exception) {
                throw e
            }
        //}
    }

    /**
     * Rolar Dados
     */
    suspend fun rollDice(token: String) {
        mutex.withLock {
            Log.d(TAG, "<ROLL> _gameState.value ${_gameState.value}")
            val current = _gameState.value ?: return

            // 1. Obtém os novos dados do serviço
            val newDiceDtos = service.rollDice(current.lobbyId, token)
            val newDice = newDiceDtos.map { it.toDie() }

            // 2. Atualiza a lista de players para que o player atual também tenha os dados
            val updatedPlayers = current.players.map { player ->
                if (player.name == current.currentPlayerName) {
                    // Se for o jogador atual, atualizamos o campo 'dice' dele
                    player.copy(dice = newDice)
                } else {
                    // Se não for, mantemos como estava
                    player
                }
            }

            // 3. Atualiza o StateFlow com o novo estado completo
            _gameState.value = current.copy(
                dice = newDice,
                players = updatedPlayers, // Injetamos a lista de jogadores atualizada
                rollsLeft = current.rollsLeft - 1,
                canRoll = (current.rollsLeft - 1) > 0
            )

            Log.d(TAG, "<ROLL> Sucesso: Dados e Jogador atualizados na UI.")
        }
    }

    suspend fun rerollDice(token: String, dicePositionsMask: List<Int>) {
        Log.d(TAG, "rerollDice: antes do withLock, mask = $dicePositionsMask")
        mutex.withLock {
            Log.d(TAG, "rerollDice: entrou no withLock")

            val current = _gameState.value
            if (current == null) {
                Log.w(TAG, "rerollDice: current gameState é null, a sair")
                return
            }

            Log.d(TAG, "rerollDice: antes do service.rerollDice, lobbyId=${current.lobbyId}")
            val newDiceDtos = service.rerollDice(current.lobbyId, token, dicePositionsMask)
            Log.d(TAG, "rerollDice: depois do service.rerollDice, recebeu ${newDiceDtos.size} dados")

            val newDice = newDiceDtos.map {
                Log.v(TAG, "rerollDice: a mapear die dto=$it")
                it.toDie()
            }

            val newRollsLeft = current.rollsLeft - 1
            Log.d(
                TAG,
                "rerollDice: antes de atualizar gameState, newRollsLeft=$newRollsLeft"
            )

            val updatedPlayers = current.players.map { player ->
                if (player.name == current.currentPlayerName) {
                    player.copy(dice = newDice)
                } else {
                    player
                }
            }

            _gameState.value = current.copy(
                dice = newDice,
                players = updatedPlayers,
                rollsLeft = current.rollsLeft - 1,
                canRoll = (current.rollsLeft - 1) > 0
            )

            Log.d(TAG, "rerollDice: gameState atualizado, canRoll=${newRollsLeft > 0}")
        }
        Log.d(TAG, "rerollDice: depois do withLock (fim da função)")

    }

    /**
     * Terminar Turno
     */
    suspend fun endTurn( token: String) {
        mutex.withLock {
            val current = _gameState.value ?: return
            Log.d(TAG, "<ENDTURN> current ${current}")

            service.endTurn(current.id, token)
            Log.d(TAG, "<ENDTURN> PASSOU O SERVICES")

            val players = current.players
            val currentIndex = players.indexOfFirst { it.isCurrentTurn }
            val nextIndex = (currentIndex + 1) % players.size

            val updatedPlayers = players.mapIndexed { index, player ->
                when (index) {
                    currentIndex -> player.copy(
                        isCurrentTurn = false,
                        dice = current.dice
                    )
                    nextIndex -> player.copy(
                        isCurrentTurn = true
                    )
                    else -> player
                }
            }

            _gameState.value = current.copy(
                players = updatedPlayers,
                currentPlayerName = updatedPlayers[nextIndex].name,
                dice = emptyList(), // Limpa os dados da mesa visualmente
                rollsLeft = 3,      // Reset para o próximo
                canRoll = true
            )

            Log.d(TAG, "<REROLL> UI e Lista de Jogadores atualizada.")
        }
    }

    /**
     * Começar Próxima Ronda
     */
    suspend fun startNextRound(token: String) {
        mutex.withLock {
            val current = _gameState.value ?: return

            // Se o service retornar o DTO da nova ronda, podes extrair o roundCounter
            service.startNextRound(current.lobbyId, token)

            _gameState.value = current.copy(
                roundNumber = current.roundNumber + 1,
                rollsLeft = 3,
                dice = emptyList(),
                canRoll = true
            )
        }
    }
}