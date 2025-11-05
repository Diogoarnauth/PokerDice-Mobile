package com.example.chelasmultiplayerpokerdice.lobby

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import kotlinx.coroutines.launch

// ----------- ESTADOS DA UI -----------
interface LobbyScreenState {
    data object Loading : LobbyScreenState
    data class Success(val lobby: Lobby) : LobbyScreenState
    data class Error(val message: String) : LobbyScreenState
}

// ----------- VIEWMODEL -----------
class LobbyScreenViewModel(private val service: LobbyService) : ViewModel() {

    var state by mutableStateOf<LobbyScreenState>(LobbyScreenState.Loading)
        private set

    init {
        loadLobby()
    }

    fun loadLobby() {
        viewModelScope.launch {
            state = LobbyScreenState.Loading
            try {
                val lobby = service.getLobby()
                state = LobbyScreenState.Success(lobby)
            } catch (e: Throwable) {
                state = LobbyScreenState.Error("Erro ao carregar lobby: ${e.message}")
            }
        }
    }
}

// ----------- FACTORY -----------
@Suppress("UNCHECKED_CAST")
class LobbyScreenViewModelFactory(private val service: LobbyService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbyScreenViewModel(service) as T
    }
}
