package com.example.chelasmultiplayerpokerdice.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


interface LobbyScreenState {
    data object Loading : LobbyScreenState
    data class Success(val lobby: Lobby) : LobbyScreenState
    data class Error(val message: String) : LobbyScreenState
}

class LobbyScreenViewModel(val service: LobbyService,val  lobbyId: Int) : ViewModel() {
    var state: StateFlow<LobbyScreenState> = service.getLobby(lobbyId)
        .map { lobby -> LobbyScreenState.Success(lobby) as LobbyScreenState }
        .catch { error -> emit(LobbyScreenState.Error("Erro ao carregar lobby: ${error.message}")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LobbyScreenState.Loading
        )

    fun onAbandon() {
        viewModelScope.launch {
            try {
                service.abandonLobby(lobbyId)
                // TODO("FALTA APAGAR O LOBBY CASO ELE SEJA O ULTIMO JOGADOR")
            } catch (e: Throwable) {
                TODO("IMPLEMENTAR ERROR HANDLING AQUI")
            }
        }
    }
}


// ----------- FACTORY -----------
@Suppress("UNCHECKED_CAST")
class LobbyScreenViewModelFactory(private val service: LobbyService, private val lobbyId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbyScreenViewModel(service, lobbyId) as T
    }
}
