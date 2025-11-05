package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ----------- ESTADO DA UI -----------
interface LobbiesScreenState {
    data object Loading : LobbiesScreenState
    data class Success(val lobbies: List<Lobby>) : LobbiesScreenState
    data class Error(val error: Throwable) : LobbiesScreenState
}

// ----------- VIEWMODEL -----------
class LobbiesViewModel(private val service: LobbiesService) : ViewModel() {

    var state by mutableStateOf<LobbiesScreenState>(LobbiesScreenState.Loading)
        private set

    init {
        loadLobbies()
    }

    fun loadLobbies() {
        viewModelScope.launch {
            try {
                // Simular atraso de rede
                delay(500)
                val lobbies = service.getLobbies()
                state = LobbiesScreenState.Success(lobbies)
            } catch (e: Throwable) {
                state = LobbiesScreenState.Error(e)
            }
        }
    }
}

// ----------- FACTORY PARA CRIAR O VIEWMODEL -----------
@Suppress("UNCHECKED_CAST")
class LobbiesViewModelFactory(private val service: LobbiesService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbiesViewModel(service) as T
    }
}
