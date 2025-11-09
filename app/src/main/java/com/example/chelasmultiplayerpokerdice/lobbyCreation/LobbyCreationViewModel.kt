package com.example.chelasmultiplayerpokerdice.lobbyCreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase

interface LobbyCreationState {
    data object Idle : LobbyCreationState
    data object Loading : LobbyCreationState
    data class Success(val newLobbyId: Int) : LobbyCreationState
    data class Error(val message: String) : LobbyCreationState
}

class LobbyCreationViewModel(private val service: LobbyCreationService) : ViewModel() {

    var state by mutableStateOf<LobbyCreationState>(LobbyCreationState.Idle)
        private set

    fun createLobby(
        name: String,
        description: String,
        maxUsers: Int,
        rounds: Int,
        hostToken: String
    ) {
        viewModelScope.launch {
            state = LobbyCreationState.Loading
            try {
                val minUsers = 2
                val minCredit = 1

                val newLobbyId = service.createLobby(
                    name = name,
                    description = description,
                    hostToken = hostToken,
                    minUsers = minUsers,
                    maxUsers = maxUsers,
                    rounds = rounds,
                    minCreditToParticipate = minCredit
                )
                state = LobbyCreationState.Success(newLobbyId)
            } catch (e: Throwable) {
                state = LobbyCreationState.Error("Erro ao criar lobby: ${e.message}")
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
class LobbyCreationViewModelFactory(private val service: LobbyCreationService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbyCreationViewModel(service) as T
    }
}
