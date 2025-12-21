package com.example.chelasmultiplayerpokerdice.lobby

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

interface LobbyScreenState {
    data object Loading : LobbyScreenState
    // Atualizado para incluir o ID do utilizador atual
    data class Success(val lobby: Lobby, val players: List<User>, val myId: Int) : LobbyScreenState
    data class Error(val message: String) : LobbyScreenState
}

class LobbyViewModel(
    private val repository: LobbyRepository
) : ViewModel() {

    private val _state = MutableStateFlow<LobbyScreenState>(LobbyScreenState.Loading)
    val state: StateFlow<LobbyScreenState> = _state.asStateFlow()

    fun loadLobby(lobbyId: Int, token: String) {
        viewModelScope.launch {
            try {
                _state.value = LobbyScreenState.Loading

                // 1. Primeiro obtemos o ID do utilizador atual (através do getMe no repository)
                val myId = repository.getMyId(token)

                // 2. Iniciamos o polling do lobby
                repository.getLobbyLive(lobbyId)
                    .catch { error ->
                        _state.value = LobbyScreenState.Error("Erro: ${error.message}")
                    }
                    .collect { details ->
                        _state.value = LobbyScreenState.Success(
                            lobby = details.lobby,
                            players = details.players,
                            myId = myId // Passamos o ID para o estado de sucesso
                        )
                    }

            } catch (e: Throwable) {
                _state.value = LobbyScreenState.Error("Erro ao carregar dados: ${e.message}")
            }
        }
    }

    fun onAbandon(lobbyId: Int, token: String) {
        viewModelScope.launch {
            try {
                repository.leaveLobby(lobbyId, token)
            } catch (e: Throwable) {
                _state.value = LobbyScreenState.Error("Erro ao sair: ${e.message}")
            }
        }
    }

    fun onJoin(lobbyId: Int, token: String) {
        viewModelScope.launch {
            try {
                repository.joinLobby(lobbyId, token)
            } catch (e: Throwable) {
                _state.value = LobbyScreenState.Error("Não foi possível entrar: ${e.message}")
            }
        }
    }

   fun onStartGame(lobbyId: Int, token: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                repository.startGame(lobbyId, token)

                onSuccess()
            } catch (e: Throwable) {
                _state.value = LobbyScreenState.Error("Erro ao iniciar jogo: ${e.message}")
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class LobbyScreenViewModelFactory(
    private val repository: LobbyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbyViewModel(repository) as T
    }
}