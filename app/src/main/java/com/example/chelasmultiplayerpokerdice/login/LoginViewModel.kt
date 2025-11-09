package com.example.chelasmultiplayerpokerdice.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface LoginScreenState {
    data object Idle : LoginScreenState
    data object Loading : LoginScreenState
    data class Success(val user: AuthenticatedUser) : LoginScreenState
    data class Error(val message: String) : LoginScreenState
}

class LoginScreenViewModel(private val service: LoginService) : ViewModel() {

    private val _state = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)

    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun fetchLogin(username: String, password: String) {
        if (_state.value is LoginScreenState.Loading) return

        viewModelScope.launch {
            _state.value = LoginScreenState.Loading

            try {
                val token = service.login(username, password)
                if (token == null)
                    _state.value = LoginScreenState.Error("Credenciais inválidas")
                else
                    _state.value = LoginScreenState.Success(AuthenticatedUser(username, token))
            } catch (e: Throwable) {
                _state.value = LoginScreenState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun resetToIdle() {
        if (_state.value is LoginScreenState.Error) {
            _state.value = LoginScreenState.Idle
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LoginScreenViewModelFactory(private val service: LoginService, ) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginScreenViewModel(service, ) as T
    }
}