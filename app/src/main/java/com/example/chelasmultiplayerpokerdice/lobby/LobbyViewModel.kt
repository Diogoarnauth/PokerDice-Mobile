package com.example.chelasmultiplayerpokerdice.lobby

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
    data class Success(val lobby: Lobby, val players: List<User>) : LobbyScreenState
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

                //repository.joinLobby(lobbyId, token)
                repository.getLobbyLive(lobbyId)
                    .catch { error ->
                        _state.value = LobbyScreenState.Error("Erro: ${error.message}")
                    }
                    .collect { details ->
                        _state.value = LobbyScreenState.Success(
                            lobby = details.lobby,
                            players = details.players
                        )
                    }

            } catch (e: Throwable) {
                _state.value = LobbyScreenState.Error("Erro ao entrar no lobby: ${e.message}")
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
}

@Suppress("UNCHECKED_CAST")
class LobbyScreenViewModelFactory(
    private val repository: LobbyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbyViewModel(repository) as T
    }
}
