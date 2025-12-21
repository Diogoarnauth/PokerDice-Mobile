package com.example.chelasmultiplayerpokerdice.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chelasmultiplayerpokerdice.auth.AuthInfoRepo
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SignupScreenState {
    data class Idle(val isBootstrapMode: Boolean): SignupScreenState
    data object Loading : SignupScreenState
    data class Success(val user: AuthenticatedUser) : SignupScreenState
    data class Error(val message: String) : SignupScreenState
}

class SignupViewModel(private val service: SignupService, private val repo: AuthInfoRepo) :
    ViewModel() {

    private val _state = MutableStateFlow<SignupScreenState>(SignupScreenState.Loading)
    val state: StateFlow<SignupScreenState> = _state.asStateFlow()

    init {
        Log.d("SignupViewModel", "Verificando estado do sistema...")
        checkSystemState()
    }

    private fun checkSystemState() {
        viewModelScope.launch {
            try {
                val isBootstrap = service.needsBootstrap()
                Log.d("SignupViewModel", "Sistema em modo Bootstrap: $isBootstrap")

                _state.value = SignupScreenState.Idle(isBootstrapMode = isBootstrap)
            } catch (e: Exception) {
                _state.value = SignupScreenState.Idle(isBootstrapMode = false)
            }
        }
    }

    fun fetchSignup(
        username: String,
        password: String,
        name: String,
        age: Int,
        inviteCode: String
    ) {
        if (_state.value is SignupScreenState.Loading) return

        _state.value = SignupScreenState.Loading
        viewModelScope.launch {
            try {
                val user = service.signup(username, password, name, age, inviteCode)
                if (user != null) {
                    repo.set(user)
                    _state.value = SignupScreenState.Success(user)
                } else {
                    _state.value = SignupScreenState.Error("Esse username já está a ser utilizado.")
                }
            } catch (e: Throwable) {
                _state.value = SignupScreenState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun resetToIdle() {
        if (_state.value is SignupScreenState.Error) {
            checkSystemState()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SignupScreenViewModelFactory(
    private val service: SignupService,
    private val repo: AuthInfoRepo
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(service, repo) as T
    }
}