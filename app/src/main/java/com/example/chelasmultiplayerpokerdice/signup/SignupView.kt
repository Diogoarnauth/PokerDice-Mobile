package com.example.chelasmultiplayerpokerdice.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.ui.theme.common.GenericOutlinedTextField
import com.example.chelasmultiplayerpokerdice.ui.theme.common.GenericTopAppBar

const val SIGNUP_VIEW_TEST_TAG = "SignupView"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignupView(
    onBack: () -> Unit,
    onGoToLogin: () -> Unit,
    onFetchSignup: (String, String, String, Int) -> Unit
) {
    Scaffold(
        topBar = {
            GenericTopAppBar(
                title = "Registo",
                modifier = Modifier.testTag(SIGNUP_VIEW_TEST_TAG),
                onBackAction = onBack
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var username by rememberSaveable { mutableStateOf("") }
                var name by rememberSaveable { mutableStateOf("") }
                var age by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                var passwordConfirm by rememberSaveable { mutableStateOf("") }
                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

                val ageInt = age.toIntOrNull() ?: 0

                val isEnabled = password == passwordConfirm &&
                        password.isNotEmpty() && username.isNotEmpty() &&
                        name.isNotEmpty() && age.isNotEmpty() && ageInt > 18

                GenericOutlinedTextField(
                    value = username,
                    onValueChange = { username = it.filter { it != ' ' && it != '\t' } },
                    label = "Username",
                )

                GenericOutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nome Completo",
                )

                GenericOutlinedTextField(
                    value = age,
                    onValueChange = { age = it.filter { it.isDigit() } },
                    label = "Idade",
                    keyboardType = KeyboardType.Number
                )

                GenericOutlinedTextField(
                    value = password,
                    onValueChange = { password = it.filter { it != ' ' && it != '\t' } },
                    label = "Password",
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = it }
                )

                GenericOutlinedTextField(
                    value = passwordConfirm,
                    onValueChange = { passwordConfirm = it.filter { it != ' ' && it != '\t' } },
                    label = "Confirmar Password",
                    isPassword = true,
                    passwordVisible = confirmPasswordVisible,
                    onPasswordVisibilityChange = { confirmPasswordVisible = it }
                )

                Button(
                    onClick = {
                        onFetchSignup(
                            username,
                            password,
                            name,
                            age.toInt()
                        )
                    },
                    shape = RoundedCornerShape(4.dp),
                    enabled = isEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Criar Conta", modifier = Modifier.padding(4.dp))
                }
                TextButton(onClick = onGoToLogin) {
                    Text("Já tens conta? Faz Login")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupViewPreview() {
    /*
    SignupView(
        onBack = { },
        onFetchSignup = { _, _, _, _ -> })*/
}