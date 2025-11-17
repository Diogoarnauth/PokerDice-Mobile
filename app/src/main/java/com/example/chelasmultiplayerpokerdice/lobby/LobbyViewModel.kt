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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


interface LobbyScreenState {
    data object Loading : LobbyScreenState
    data class Success(val lobby: Lobby,val players: List<User>) : LobbyScreenState
    data class Error(val message: String) : LobbyScreenState
}

class LobbyViewModel(
    val service: LobbyService,
) : ViewModel() {
    private val _state = MutableStateFlow<LobbyScreenState>(LobbyScreenState.Loading)
    val state: StateFlow<LobbyScreenState> = _state.asStateFlow()


    fun loadLobby(lobbyId: Int, token: String) {
        if (_state.value is LobbyScreenState.Success) return

        viewModelScope.launch {
            try {
                service.joinLobby(lobbyId, token)

                val lobbyFlow = service.getLobby(lobbyId)

                val playersFlow = service.getLobbyPlayersFlow(lobbyId)

                lobbyFlow.combine(playersFlow) { lobby, players ->
                    LobbyScreenState.Success(lobby, players)
                }
                    .catch { error ->
                        _state.value = LobbyScreenState.Error("Erro: ${error.message}")
                    }
                    .collect { successState ->
                        _state.value = successState
                    }

            } catch (e: Throwable) {
                _state.value = LobbyScreenState.Error("Erro ao entrar no lobby: ${e.message}")
            }
        }
    }

    fun onAbandon(lobbyId: Int, token: String) {
        viewModelScope.launch {
            try {
                service.abandonLobby(lobbyId, token)
            } catch (e: Throwable) {
                _state.value = LobbyScreenState.Error("Erro ao abandonar o lobby: ${e.message}")
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
class LobbyScreenViewModelFactory(
    private val service: LobbyService
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbyViewModel(service) as T
    }
}