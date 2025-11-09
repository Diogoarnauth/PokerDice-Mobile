package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.signup.SignupNavigation
import com.example.chelasmultiplayerpokerdice.signup.SignupScreen
import com.example.chelasmultiplayerpokerdice.signup.SignupScreenViewModelFactory
import com.example.chelasmultiplayerpokerdice.signup.SignupViewModel

class SignupActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val signupNavigation: SignupNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val signupViewModel: SignupViewModel =
                viewModel(factory = SignupScreenViewModelFactory(app.signupService))

            SignupScreen(
                viewModel = signupViewModel,
                navigator = signupNavigation
            )
        }
    }
}