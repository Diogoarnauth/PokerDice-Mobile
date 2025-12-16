package com.example.chelasmultiplayerpokerdice.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.auth.AuthInfoRepo
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

class LoginScreenViewModel(private val service: LoginService, private val repo: AuthInfoRepo) :
    ViewModel() {

    private val _state = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)

    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun fetchLogin(username: String, password: String) {
        if (_state.value is LoginScreenState.Loading) return

        viewModelScope.launch {
            _state.value = LoginScreenState.Loading

            try {
                val user = service.login(username, password)
                if (user != null) {
                    repo.set(user)
                    _state.value = LoginScreenState.Success(user)
                } else {
                    _state.value = LoginScreenState.Error("Username ou password incorretos.")
                }
            } catch (e: Throwable) {
                _state.value = LoginScreenState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun resetToIdle() {
        _state.value = LoginScreenState.Idle
    }
}

@Suppress("UNCHECKED_CAST")
class LoginScreenViewModelFactory(
    private val service: LoginService,
    private val repo: AuthInfoRepo
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginScreenViewModel(service, repo) as T
    }
}