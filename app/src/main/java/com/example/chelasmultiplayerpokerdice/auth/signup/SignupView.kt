package com.example.chelasmultiplayerpokerdice.auth.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chelasmultiplayerpokerdice.ui.theme.common.GenericTopAppBar
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGold
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText

const val SIGNUP_VIEW_TEST_TAG = "SignupView"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignupView(
    onBack: () -> Unit,
    onGoToLogin: () -> Unit,
    onFetchSignup: (String, String, String, Int, String) -> Unit,
    isBootstrapMode: Boolean
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
                .background(
                    Brush.verticalGradient(
                        listOf(pokerGreen, pokerGreenDark)
                    )
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 32.dp, bottom = 16.dp)
                    .background(Color(0xCC061F17), RoundedCornerShape(16.dp))
                    .padding(24.dp)
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
                var inviteCode by rememberSaveable { mutableStateOf("") }

                val ageInt = age.toIntOrNull() ?: 0

                val isEnabled = password == passwordConfirm &&
                        password.isNotEmpty() && username.isNotEmpty() &&
                        name.isNotEmpty() && age.isNotEmpty() && ageInt > 18

                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = pokerGreen,
                    unfocusedBorderColor = pokerGreen.copy(alpha = 0.7f),
                    focusedTextColor = pokerText,
                    unfocusedTextColor = pokerText,
                    focusedLabelColor = pokerGreen,
                    unfocusedLabelColor = pokerGreen
                )

                Text(
                    text = "Create your account",
                    color = pokerGreen,
                    modifier = Modifier.padding(bottom = 16.dp)
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
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome Completo") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = textFieldColors
                )

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Idade") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

                OutlinedTextField(
                    value = passwordConfirm,
                    onValueChange = { passwordConfirm = it.filter { c -> c != ' ' && c != '\t' } },
                    label = { Text("Confirmar Password") },
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        TextButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Text(
                                text = if (confirmPasswordVisible) "Esconder" else "Mostrar",
                                color = pokerGreen
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = textFieldColors
                )

                if (!isBootstrapMode) {
                    OutlinedTextField(
                        value = inviteCode,
                        onValueChange = { inviteCode = it },
                        label = { Text("Código de Convite") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = textFieldColors
                    )
                }

                Button(
                    onClick = {
                        onFetchSignup(
                            username,
                            password,
                            name,
                            age.toInt(),
                            inviteCode
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    enabled = isEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed,
                        disabledContainerColor = pokerGold.copy(alpha = 0.4f),
                        disabledContentColor = pokerRed.copy(alpha = 0.6f)
                    )
                ) {
                    Text("Criar Conta", modifier = Modifier.padding(4.dp))
                }

                TextButton(onClick = onGoToLogin, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Já tens conta? Faz Login", color = pokerGreen)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupViewPreview() {
    SignupView(
        onBack = { },
        onGoToLogin = { },
        onFetchSignup = { _, _, _, _, _ -> },
        isBootstrapMode = false
    )
}
