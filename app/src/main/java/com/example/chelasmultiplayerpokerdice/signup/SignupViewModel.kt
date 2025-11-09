package com.example.chelasmultiplayerpokerdice.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SignupScreenState {
    data object Idle : SignupScreenState
    data object Loading : SignupScreenState
    data class Success(val user: AuthenticatedUser) : SignupScreenState
    data class Error(val message: String) : SignupScreenState
}

class SignupViewModel(private val service: SignupService) : ViewModel() {

    private val _state = MutableStateFlow<SignupScreenState>(SignupScreenState.Idle)
    val state: StateFlow<SignupScreenState> = _state.asStateFlow()

    fun fetchSignup(username: String, password: String, name: String, age: Int) {
        if (_state.value is SignupScreenState.Loading) return

        _state.value = SignupScreenState.Loading
        viewModelScope.launch {
            _state.value = try {
                val user = service.signup(username, password, name, age)
                if (user != null) {
                    SignupScreenState.Success(user)
                } else {
                    SignupScreenState.Error("Esse username já está a ser utilizado.")
                }
            } catch (e: Throwable) {
                SignupScreenState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun resetToIdle() {
        if (_state.value is SignupScreenState.Error) {
            _state.value = SignupScreenState.Idle
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SignupScreenViewModelFactory(private val service: SignupService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(service) as T
    }
}