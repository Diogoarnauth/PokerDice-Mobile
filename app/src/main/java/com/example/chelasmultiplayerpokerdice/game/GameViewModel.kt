package com.example.chelasmultiplayerpokerdice.game

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

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    private val _state = MutableStateFlow<GameScreenState>(GameScreenState.Loading)
    val state: StateFlow<GameScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.gameState.collectLatest { newGameState ->
                Log.d(TAG, "newGameState $newGameState")
                if (newGameState == null) {
                    _state.value = GameScreenState.Loading
                } else {
                    val isRoundOver = newGameState.players.all { it.dice != null }
                    val isGameOver = newGameState.finalWinners.isNotEmpty()

                    _state.value = when {
                        isGameOver -> GameScreenState.GameOver(
                            newGameState,
                            newGameState.finalWinners
                        )

                        isRoundOver -> {
                            val winner = newGameState.players.maxByOrNull { it.hand?.score ?: 0.0 }
                                ?: newGameState.players.first()
                            GameScreenState.RoundOver(newGameState, winner)
                        }

                        else -> GameScreenState.Playing(newGameState)
                    }
                }
            }
        }
    }

    fun loadGame(lobbyId: Int, token: String) {
        Log.d("loadGameViewModel", "ENTREI NO loadGame")
        viewModelScope.launch {
            try {
                repository.fetchGame(lobbyId, token)
            } catch (e: Throwable) {
                _state.value = GameScreenState.Error(e.message ?: "Erro ao carregar jogo")
            }
        }
    }

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

    fun onEndTurnClicked(token: String) {
        viewModelScope.launch {
            try {
                _state.value = GameScreenState.Loading
                repository.endTurn(token)
            } catch (e: Throwable) {
                _state.value = GameScreenState.Error("Erro ao terminar turno")
            }
        }
    }

    fun onStartNextRound() {
        viewModelScope.launch {
            try {
                repository.startNextRound()
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