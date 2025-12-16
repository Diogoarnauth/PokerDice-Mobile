package com.example.chelasmultiplayerpokerdice.game

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
/**
 * GameRepository
 * Responsabilidade: Guardar o estado atual do jogo (StateFlow) e sincronizar o acesso (Mutex).
 * Atualmente usa o GameService (Fake), mas no futuro usará a API.
 */

class GameRepository(private val service: GameService) {

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState.asStateFlow()

    private val mutex = Mutex()

    /**
     * Inicia o jogo (Vai ao serviço buscar o estado inicial e emite no Flow)
     */
    suspend fun fetchGame(lobbyId: Int, token: String) {
        mutex.withLock {
            try {
                val initialState = service.getInitialGameState(lobbyId, token)
                _gameState.value = initialState
            } catch (e: Exception) {
                throw e
            }
        }
    }

    /**
     * Rolar Dados
     */
    suspend fun rollDice(token: String) {
        mutex.withLock {
            val current = _gameState.value ?: return
            // Chama o serviço (lógica)
            val newState = service.rollDice(current, token)
            // Atualiza o estado
            _gameState.value = newState
        }
    }

    /**
     * Terminar Turno
     */
    suspend fun endTurn(token: String) {
        mutex.withLock {
            val current = _gameState.value ?: return
            val newState = service.endTurnAndSimulate(current, token)
            _gameState.value = newState
        }
    }

    /**
     * Começar Próxima Ronda
     */
    suspend fun startNextRound() {
        mutex.withLock {
            val current = _gameState.value ?: return
            val newState = service.startNextRound(current)
            _gameState.value = newState
        }
    }
}