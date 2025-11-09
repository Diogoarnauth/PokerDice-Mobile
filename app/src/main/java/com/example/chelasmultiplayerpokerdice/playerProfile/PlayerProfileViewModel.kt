package com.example.chelasmultiplayerpokerdice.playerProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface PlayerProfileScreenState {
    data object Loading : PlayerProfileScreenState
    data class Success(val data: User) : PlayerProfileScreenState
    data class Error(val message: String) : PlayerProfileScreenState
}

class PlayerProfileViewModel(private val service: PlayerProfileService) : ViewModel() {

    private val _state = MutableStateFlow<PlayerProfileScreenState>(PlayerProfileScreenState.Loading)
    val state: StateFlow<PlayerProfileScreenState> = _state.asStateFlow()


    fun loadProfile(token: String?) {
        viewModelScope.launch {
            _state.value = PlayerProfileScreenState.Loading
            try {
                val data = service.getPlayerProfileData(token)
                _state.value = PlayerProfileScreenState.Success(data)
            } catch (e: Throwable) {
                _state.value = PlayerProfileScreenState.Error("Erro ao carregar perfil: ${e.message}")
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

