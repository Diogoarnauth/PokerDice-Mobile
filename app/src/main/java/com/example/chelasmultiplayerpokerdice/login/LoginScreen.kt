package com.example.chelasmultiplayerpokerdice.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.chelasmultiplayerpokerdice.ui.theme.common.ErrorAlert
import com.example.chelasmultiplayerpokerdice.ui.theme.common.LoadingView

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel, navigator: LoginNavigation) {

    val currentState by viewModel.state.collectAsState()

    when (currentState) {
        is LoginScreenState.Idle -> {
            LoginView(
                onFetchLogin = { username, password -> viewModel.fetchLogin(username, password) },
                onGoToSignup = { navigator.goToSignupScreen() }
            )
        }
        is LoginScreenState.Loading -> {
            LoadingView(text = "A fazer Login...")
        }
        is LoginScreenState.Error -> {
            ErrorAlert(
                title = "Erro no Login",
                message = (currentState as LoginScreenState.Error).message,
                buttonText = "Tentar Novamente",
                onDismiss = { viewModel.resetToIdle() }
            )
        }
        is LoginScreenState.Success -> {
            navigator.goToTitleScreen((currentState as LoginScreenState.Success).user)
        }
    }
}