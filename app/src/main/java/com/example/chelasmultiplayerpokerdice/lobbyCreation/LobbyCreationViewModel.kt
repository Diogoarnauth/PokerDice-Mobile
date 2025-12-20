package com.example.chelasmultiplayerpokerdice.lobbyCreation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.TAG
import kotlinx.coroutines.launch
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase

interface LobbyCreationState {
    data object Idle : LobbyCreationState
    data object Loading : LobbyCreationState
    data object Success : LobbyCreationState
    data class Error(val message: String) : LobbyCreationState
}

class LobbyCreationViewModel(private val service: LobbyCreationService) : ViewModel() {

    var state by mutableStateOf<LobbyCreationState>(LobbyCreationState.Idle)
        private set

    fun clearError() {
        state = LobbyCreationState.Idle
    }
    fun createLobby(
        name: String,
        description: String,
        minUsers: Int,
        maxUsers: Int,
        rounds: Int,
        minCreditToParticipate: Int,
        hostToken: String
    ) {
        viewModelScope.launch {
            state = LobbyCreationState.Loading
            try {


               service.createLobby(
                    name = name,
                    description = description,
                    hostToken = hostToken,
                    minUsers = minUsers,
                    maxUsers = maxUsers,
                    rounds = rounds,
                    minCreditToParticipate = minCreditToParticipate
                )

                state = LobbyCreationState.Success

            } catch (e: Throwable) {
                Log.d(TAG, "Erro ao criar lobby: ${e.message}")
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
