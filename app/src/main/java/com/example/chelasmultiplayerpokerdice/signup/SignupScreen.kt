package com.example.chelasmultiplayerpokerdice.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.chelasmultiplayerpokerdice.ui.theme.common.ErrorAlert
import com.example.chelasmultiplayerpokerdice.ui.theme.common.LoadingView

@Composable
fun SignupScreen(viewModel: SignupViewModel, navigator: SignupNavigation) {

    val currentState by viewModel.state.collectAsState()

    when (currentState) {
        is SignupScreenState.Idle -> {
            SignupView(
                onBack = { navigator.goToLoginScreen() },
                onFetchSignup = { username, password, name, age ->
                    viewModel.fetchSignup(username, password, name, age)
                },
                onGoToLogin = { navigator.goToLoginScreen() }
            )
        }

        is SignupScreenState.Loading -> {
            LoadingView(text = "A criar conta...")
        }

        is SignupScreenState.Error -> {
            ErrorAlert(
                title = "Erro no Registo",
                message = (currentState as SignupScreenState.Error).message,
                buttonText = "Tentar Novamente",
                onDismiss = { viewModel.resetToIdle() }
            )
        }

        is SignupScreenState.Success -> {
            navigator.goToLoginScreen()
        }
    }

}