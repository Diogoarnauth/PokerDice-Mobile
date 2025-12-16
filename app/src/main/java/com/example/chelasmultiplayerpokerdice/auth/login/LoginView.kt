package com.example.chelasmultiplayerpokerdice.auth.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.ui.theme.common.GenericOutlinedTextField
import com.example.chelasmultiplayerpokerdice.ui.theme.common.GenericTopAppBar

const val LOGIN_VIEW_TEST_TAG = "LoginView"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3Code")
@Composable
fun LoginView(
    onFetchLogin: (String, String) -> Unit,
    onGoToSignup: () -> Unit
) {
    Scaffold(
        topBar = {
            GenericTopAppBar(
                title = "Login",
                modifier = Modifier.testTag(LOGIN_VIEW_TEST_TAG)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 32.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var username by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                var passwordVisible by rememberSaveable { mutableStateOf(false) }

                GenericOutlinedTextField(
                    value = username,
                    onValueChange = { username = it.filter { it != ' ' && it != '\t' } },
                    label = "Username",
                )

                GenericOutlinedTextField(
                    value = password,
                    onValueChange = { password = it.filter { it != ' ' && it != '\t' } },
                    label = "Password",
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = it }
                )

                Button(
                    onClick = { onFetchLogin(username, password) },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Login", modifier = Modifier.padding(4.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onGoToSignup) {
                    Text("Ainda não tens conta? Regista-te")
                }
            }
        }
    }
}