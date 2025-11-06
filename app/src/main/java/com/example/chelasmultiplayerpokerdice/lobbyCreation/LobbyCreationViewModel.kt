package com.example.chelasmultiplayerpokerdice.lobbyCreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.Player
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
        rounds: Int
    ) {
        viewModelScope.launch {
            state = LobbyCreationState.Loading
            try {
                // Preenche os dados em falta
                val hostId = FakeDatabase.myUser.id // Pega o ID do "host" logado
                val minUsers = 2 // Requisito do enunciado [cite: 91]
                val minCredit = 1 // Requisito do enunciado (ante) [cite: 14]

                val newLobbyId = service.createLobby(
                    name = name,
                    description = description,
                    hostId = hostId,
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
