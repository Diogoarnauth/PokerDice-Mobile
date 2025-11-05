package com.example.chelasmultiplayerpokerdice.playerProfileScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ---------- ESTADOS ----------
interface PlayerProfileState {
    data object Loading : PlayerProfileState
    data class Success(val data: PlayerProfileData) : PlayerProfileState
    data class Error(val message: String) : PlayerProfileState
}

// ---------- VIEWMODEL ----------
class PlayerProfileViewModel(private val service: PlayerProfileService) : ViewModel() {

    var state by mutableStateOf<PlayerProfileState>(PlayerProfileState.Loading)
        private set

    fun loadProfile(username: String) {
        viewModelScope.launch {
            try {
                val data = service.getPlayerProfileData(username)
                state = PlayerProfileState.Success(data)
            } catch (e: Throwable) {
                state = PlayerProfileState.Error("Erro ao carregar perfil: ${e.message}")
            }
        }
    }
}

// ---------- FACTORY ----------
@Suppress("UNCHECKED_CAST")
class PlayerProfileViewModelFactory(private val service: PlayerProfileService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerProfileViewModel(service) as T
    }
}

