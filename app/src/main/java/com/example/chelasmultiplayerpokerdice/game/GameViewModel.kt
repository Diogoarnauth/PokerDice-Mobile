package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val service: GameService) : ViewModel() {

    private val _state = MutableStateFlow<GameScreenState>(GameScreenState.Loading)
    val state: StateFlow<GameScreenState> = _state.asStateFlow()

    fun loadGame(lobbyId: Int, token: String) {
        if (_state.value is GameScreenState.Playing) return
        viewModelScope.launch {
            try {
                _state.value = GameScreenState.Loading
                val initialState = service.getInitialGameState(lobbyId, token)
                _state.value = GameScreenState.Playing(initialState)
            } catch (e: Throwable) {
                _state.value = GameScreenState.Error(e.message ?: "Erro ao carregar jogo")
            }
        }
    }


    fun onDieClicked(dieId: Int) {
        val currentState = _state.value
        if (currentState is GameScreenState.Playing) {
            val newDice = currentState.gameState.dice.map {
                if (it.id == dieId) it.copy(isHeld = !it.isHeld) else it
            }
            _state.value = GameScreenState.Playing(
                currentState.gameState.copy(dice = newDice)
            )
        }
    }

    fun onRollClicked(token: String) {
        val currentState = _state.value
        if (currentState is GameScreenState.Playing && currentState.gameState.canRoll) {
            viewModelScope.launch {
                try {
                    val newState = service.rollDice(currentState.gameState, token)
                    _state.value = GameScreenState.Playing(newState)
                } catch (e: Throwable) {
                    _state.value = GameScreenState.Error(e.message ?: "Erro ao rolar")
                }
            }
        }
    }


    fun onEndTurnClicked(token: String) {
        val currentState = _state.value
        if (currentState is GameScreenState.Playing) {
            viewModelScope.launch {
                try {
                    _state.value =
                        GameScreenState.Loading // TODO("MUDAR PARA A PREPARAR TURNO) adicionar param ao loading ?

                    val newState = service.endTurnAndSimulate(currentState.gameState, token)

                    val allPlayersHavePlayed = newState.players.all { it.dice != null }

                    if (allPlayersHavePlayed) {

                        val roundWinner = newState.players.maxBy { it.hand!!.score }
                        _state.value = GameScreenState.RoundOver(newState, roundWinner)

                    } else _state.value = GameScreenState.Playing(newState)

                } catch (e: Throwable) {
                    _state.value = GameScreenState.Error(e.message ?: "Erro ao terminar turno")
                }
            }
        }
    }


    fun onStartNextRound() {
        val currentState = _state.value

        if (currentState is GameScreenState.RoundOver) {
            viewModelScope.launch {
                try {
                    _state.value = GameScreenState.Loading // TODO("MUDAR PARA A PREPARAR ROUND")

                    val nextRoundState = service.startNextRound(currentState.gameState)

                    if (nextRoundState.finalWinners.isNotEmpty()) {

                        _state.value = GameScreenState.GameOver(
                            nextRoundState, nextRoundState.finalWinners
                        )
                    } else {
                        _state.value = GameScreenState.Playing(nextRoundState)
                    }
                } catch (e: Throwable) {
                    _state.value = GameScreenState.Error(e.message ?: "Erro ao iniciar ronda")
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(private val service: GameService):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(service) as T
    }
}
