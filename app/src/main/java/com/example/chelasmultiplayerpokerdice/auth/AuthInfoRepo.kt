package com.example.chelasmultiplayerpokerdice.auth

import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface AuthInfoRepo {
    val user: StateFlow<AuthenticatedUser?>
    suspend fun set(user: AuthenticatedUser)
    suspend fun remove()
}

class InMemoryAuthRepo : AuthInfoRepo {
    private val _user = MutableStateFlow<AuthenticatedUser?>(null)
    override val user: StateFlow<AuthenticatedUser?> = _user.asStateFlow()

    override suspend fun set(user: AuthenticatedUser) {
        _user.value = user
    }

    override suspend fun remove() {
        _user.value = null
    }
}