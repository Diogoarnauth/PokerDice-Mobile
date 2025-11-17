package com.example.chelasmultiplayerpokerdice.lobbies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.Lobby
import com.example.chelasmultiplayerpokerdice.domain.LobbyInfo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

interface LobbiesScreenState {
    data object Loading : LobbiesScreenState
    data class Success(val lobbies: List<LobbyInfo>) : LobbiesScreenState
    data class Error(val error: Throwable) : LobbiesScreenState
}

class LobbiesViewModel(service: LobbiesService) : ViewModel() {

    val state: StateFlow<LobbiesScreenState> = service.getLobbiesInfo()
        .map { lobbiesInfo -> LobbiesScreenState.Success(lobbiesInfo) as LobbiesScreenState }
        .catch { error -> emit(LobbiesScreenState.Error(error)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LobbiesScreenState.Loading
        )

}

@Suppress("UNCHECKED_CAST")
class LobbiesViewModelFactory(private val service: LobbiesService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LobbiesViewModel(service) as T
    }
}
