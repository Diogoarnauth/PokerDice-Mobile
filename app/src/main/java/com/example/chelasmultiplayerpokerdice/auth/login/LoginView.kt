package com.example.chelasmultiplayerpokerdice.auth.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.ui.theme.common.GenericTopAppBar
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGold
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText

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
                .background(
                    Brush.verticalGradient(
                        listOf(pokerGreen, pokerGreenDark)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .background(Color(0xCC061F17), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var username by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                var passwordVisible by rememberSaveable { mutableStateOf(false) }

                Text(
                    text = "Welcome back",
                    color = pokerGreen,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = pokerGreen,
                    unfocusedBorderColor = pokerGreen.copy(alpha = 0.7f),
                    focusedTextColor = pokerText,
                    unfocusedTextColor = pokerText,
                    focusedLabelColor = pokerGreen,
                    unfocusedLabelColor = pokerGreen
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it.filter { c -> c != ' ' && c != '\t' } },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = textFieldColors
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it.filter { c -> c != ' ' && c != '\t' } },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        TextButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(
                                text = if (passwordVisible) "Esconder" else "Mostrar",
                                color = pokerGreen
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = textFieldColors
                )

                Button(
                    onClick = { onFetchLogin(username, password) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    )
                ) {
                    Text("Login", modifier = Modifier.padding(4.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onGoToSignup) {
                    Text(
                        "Ainda não tens conta? Regista-te",
                        color = pokerGreen
                    )
                }
            }
        }
    }
}
