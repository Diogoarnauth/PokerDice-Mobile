package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ---------- ESTADOS ----------
interface LobbyCreationState {
    data object Idle : LobbyCreationState
    data object Loading : LobbyCreationState
    data class Success(val newLobbyId: Int) : LobbyCreationState
    data class Error(val message: String) : LobbyCreationState
}

// ---------- VIEWMODEL ----------
class LobbyCreationViewModel(private val service: LobbyCreationService) : ViewModel() {

    var state by mutableStateOf<LobbyCreationState>(LobbyCreationState.Idle)
        private set

    fun createLobby(
        id: Int,
        name: String,
        owner: String,
        description: String,
        rounds: Int,
        isPrivate: Boolean,
        password: String?,
        playersCount: Int,
        maxPlayers: Int,
        players: List<Player>
    ) {
        viewModelScope.launch {
            state = LobbyCreationState.Loading
            try {
                val newLobbyId = service.createLobby(
                    id,
                    name,
                    owner,
                    description,
                    rounds,
                    isPrivate,
                    password,
                    playersCount,
                    maxPlayers,
                    players
                )
                state = LobbyCreationState.Success(newLobbyId)
            } catch (e: Throwable) {
                state = LobbyCreationState.Error("Erro ao criar lobby: ${e.message}")
            }
        }
    }
}

// ---------- FACTORY ----------
@Suppress("UNCHECKED_CAST")
class LobbyCreationViewModelFactory(private val service: LobbyCreationService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbyCreationViewModel(service) as T
    }
}
