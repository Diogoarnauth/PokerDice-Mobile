package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.auth.login.LoginNavigation
import com.example.chelasmultiplayerpokerdice.auth.login.LoginScreen
import com.example.chelasmultiplayerpokerdice.auth.login.LoginScreenViewModel
import com.example.chelasmultiplayerpokerdice.auth.login.LoginScreenViewModelFactory

class LoginActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val loginNavigation: LoginNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            enableEdgeToEdge()
            setContent {

                val viewModel: LoginScreenViewModel =
                    viewModel(factory = LoginScreenViewModelFactory(app.loginService, app.authRepo))

                LoginScreen(
                    viewModel = viewModel,
                    navigator = loginNavigation
                )
            }
        }
    }