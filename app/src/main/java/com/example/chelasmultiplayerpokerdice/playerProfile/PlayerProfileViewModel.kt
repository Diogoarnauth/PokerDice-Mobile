package com.example.chelasmultiplayerpokerdice.playerProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

interface PlayerProfileScreenState {
    data object Loading : PlayerProfileScreenState
    data class Success(val data: PlayerProfileData) : PlayerProfileScreenState
    data class Error(val message: String) : PlayerProfileScreenState
}

class PlayerProfileViewModel(private val service: PlayerProfileService) : ViewModel() {

    var state by mutableStateOf<PlayerProfileScreenState>(PlayerProfileScreenState.Loading)
        private set

    init {
        loadProfile("Renata")
    }

    fun loadProfile(username: String) {
        viewModelScope.launch {
            try {
                val data = service.getPlayerProfileData(username)
                state = PlayerProfileScreenState.Success(data)
            } catch (e: Throwable) {
                state = PlayerProfileScreenState.Error("Erro ao carregar perfil: ${e.message}")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PlayerProfileViewModelFactory(private val service: PlayerProfileService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerProfileViewModel(service) as T
    }
}

