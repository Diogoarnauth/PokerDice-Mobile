package com.example.chelasmultiplayerpokerdice.lobbyCreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.Player
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
        hostId: Int,
        description: String,
        rounds: Int,
        minUsers: Int,
        maxUsers: Int,
        minCreditToParticipate: Int,
        playersCount: Int,
        players: List<Player>
    ) {
        viewModelScope.launch {
            state = LobbyCreationState.Loading
            try {
                val newLobbyId = service.createLobby(
                    name,
                    description,
                    hostId,
                    rounds,
                    minUsers,
                    maxUsers,
                    minCreditToParticipate,
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
