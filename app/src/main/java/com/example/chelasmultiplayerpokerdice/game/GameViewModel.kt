package com.example.chelasmultiplayerpokerdice.game

import android.R.id.mask
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    private val _state = MutableStateFlow<GameScreenState>(GameScreenState.Loading)
    val state: StateFlow<GameScreenState> = _state.asStateFlow()

    // Controla se o popup deve estar visível
    private val _showRoundOverDialog = MutableStateFlow(false)
    val showRoundOverDialog: StateFlow<Boolean> = _showRoundOverDialog.asStateFlow()

    private var pollingJob: kotlinx.coroutines.Job? = null
    private var myCachedUsername: String? = null
    private var cachedLobbyId: Int = -1
    private var cachedToken: String = ""


    init {
        viewModelScope.launch {
            repository.gameState.collectLatest { newGameState ->
                if (newGameState != null) {
                    updateScreenState(newGameState)

                    // REMOVE A LÓGICA DE PARAR O POLLING
                    // Se queres que o Host veja as atualizações do Guest, mantém o polling ligado.
                    if (pollingJob == null || !pollingJob!!.isActive) {
                        Log.d(TAG, "Garantindo que o Polling está ativo para sincronização.")
                        startPolling(cachedLobbyId, cachedToken)
                    }
                }
            }
        }
    }



    private fun updateScreenState(newGameState: GameState) {
        // Obtemos o estado anterior para comparar o RoundNumber
        val previousGameState = when (val s = _state.value) {
            is GameScreenState.Playing -> s.gameState
            is GameScreenState.RoundOver -> s.gameState
            else -> null
        }

        val isGameOver = newGameState.finalWinners.isNotEmpty()

        // A ronda acabou se o número da ronda no servidor aumentou
        val isRoundOver = previousGameState != null &&
                newGameState.roundNumber > previousGameState.roundNumber

        if (isRoundOver) {
            _showRoundOverDialog.value = true
        }

        _state.value = when {
            isGameOver -> GameScreenState.GameOver(newGameState, newGameState.finalWinners)
            isRoundOver -> {
                // Lógica de vencedor (podes ajustar conforme os teus dados de Hand)
                val winner = newGameState.players.maxByOrNull { it.hand?.score ?: 0.0 }
                    ?: newGameState.players.first()
                GameScreenState.RoundOver(newGameState, winner)
            }
            else -> GameScreenState.Playing(newGameState)
        }
    }
    private fun startPolling(lobbyId: Int, token: String) {
        if (pollingJob?.isActive == true) return
        pollingJob = viewModelScope.launch {
            repository.getGameLive(lobbyId, token).collect {
                // O repository já atualiza o _gameState.value,
                // o collect aqui serve apenas para manter o flow vivo.
            }
        }
    }
    // Função para fechar o diálogo sem interagir com o Repository
    fun dismissRoundOverDialog() {
        _showRoundOverDialog.value = false

        // Se estávamos em modo RoundOver, voltamos para Playing para libertar a UI
        val currentState = _state.value
        if (currentState is GameScreenState.RoundOver) {
            _state.value = GameScreenState.Playing(currentState.gameState)
        }
    }

    fun loadGame(lobbyId: Int, token: String, username: String) {
        this.myCachedUsername = username
        this.cachedLobbyId = lobbyId
        this.cachedToken = token

        viewModelScope.launch {
            try {
                repository.fetchGame(lobbyId, token)
            } catch (e: Throwable) {
                _state.value = GameScreenState.Error(e.message ?: "Erro ao carregar")
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel()
    }

    // --- Ações de Jogo ---

    fun onRollClicked(token: String) {
        val currentState = _state.value
        if (currentState is GameScreenState.Playing && currentState.gameState.canRoll) {
            viewModelScope.launch {
                try {
                    repository.rollDice(token)
                } catch (e: Throwable) {
                    _state.value = GameScreenState.Error("Erro ao rodar os dados")
                }
            }
        }
    }

    fun onRerollClicked(token: String, selectedDieIds: List<Int>) {
        viewModelScope.launch {
            try {
                repository.rerollDice(token, selectedDieIds)
            } catch (e: Throwable) {
                _state.value = GameScreenState.Error("Erro ao repetir lançamento")
            }
        }
    }

    fun onEndTurnClicked(token: String) {
        viewModelScope.launch {
            try {
                // Opcional: _state.value = GameScreenState.Loading
                repository.endTurn(token)
            } catch (e: Throwable) {
                _state.value = GameScreenState.Error("Erro ao terminar turno")
            }
        }
    }

    fun onStartNextRound(token: String) {
        viewModelScope.launch {
            try {
                repository.startNextRound(token)
            } catch (e: Throwable) {
                _state.value = GameScreenState.Error("Erro ao iniciar ronda")
            }
        }
    }

    fun onDieClicked(dieId: Int) {
        val currentState = _state.value
        if (currentState is GameScreenState.Playing) {
            val newDice = currentState.gameState.dice.map {
                if (it.id == dieId) it.copy(isHeld = !it.isHeld) else it
            }
            _state.value = GameScreenState.Playing(currentState.gameState.copy(dice = newDice))
        }
    }
}

// TODO ("REROLL DOS LOBBIES DEIXOU DE FUNCIONAR DEVIDAMENTE")

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(repository) as T
    }
}