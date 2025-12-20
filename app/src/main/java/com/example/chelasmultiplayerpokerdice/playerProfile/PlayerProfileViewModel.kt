package com.example.chelasmultiplayerpokerdice.playerProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface PlayerProfileScreenState {
    data object Loading : PlayerProfileScreenState
    data class Success(
        val data: User,
        val inviteCode: String? = null
    ) : PlayerProfileScreenState    data class Error(val message: String) : PlayerProfileScreenState
}

class PlayerProfileViewModel(private val service: PlayerProfileService) : ViewModel() {

    private val _state = MutableStateFlow<PlayerProfileScreenState>(PlayerProfileScreenState.Loading)
    val state: StateFlow<PlayerProfileScreenState> = _state.asStateFlow()


    fun loadProfile(token: String) {
        Log.d(TAG, "🚀 Token que chega ao loadProfile $token")

        viewModelScope.launch {
            _state.value = PlayerProfileScreenState.Loading
            try {
                val data = service.getPlayerProfileData(token)
                Log.d(TAG, "🚀 Data no loadProfile '$data'")
                _state.value = PlayerProfileScreenState.Success(data)
            } catch (e: Throwable) {
                _state.value = PlayerProfileScreenState.Error("Erro ao carregar perfil: ${e.message}")
            }
        }
    }

    fun generateInvite(token: String) {
        val currentState = _state.value
        // Só tentamos gerar se já tivermos o perfil carregado com sucesso
        if (currentState is PlayerProfileScreenState.Success) {
            viewModelScope.launch {
                try {
                    val code = service.getAppInvite(token)
                    // Atualizamos o estado mantendo os dados do user e adicionando o código
                    _state.value = currentState.copy(inviteCode = code)
                } catch (e: Throwable) {
                    Log.e(TAG, "Erro ao gerar convite: ${e.message}")
                    // Opcional: Podias atualizar o estado para um erro específico
                }
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

