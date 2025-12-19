package com.example.chelasmultiplayerpokerdice.title

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface TitleScreenState{
    data class Success(val creators: List<String>) : TitleScreenState
}

class TitleViewModel(private val service : TitleService) : ViewModel() {
    var state: TitleScreenState by mutableStateOf(TitleScreenState.Success(service.getCreators()))
        private set
}

@Suppress("UNCHECKED_CAST")
class TitleViewModelFactory(private val service: TitleService): ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass: Class<T>): T {
        return TitleViewModel(service) as T
    }
}
