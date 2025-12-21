package com.example.chelasmultiplayerpokerdice.playerProfile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGold
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText

const val PLAYERPROFILE_BACK_TITLESCREEN = "User Profile back to title screen"
const val PLAYERPROFILE_VIEW_TAG = "User Profile View"
const val GET_INVITE_BUTTON_TAG = "Get Invite Button"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProfileView(
    playerData: User,
    onDeposit: (Int) -> Unit,
    inviteCode: String?,
    goBackTitleScreenFunction: () -> Unit,
    onGetInviteCode: () -> Unit
) {
    var depositValue by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(PLAYERPROFILE_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile: ${playerData.username}",
                        style = MaterialTheme.typography.titleLarge,
                        color = pokerText
                    )
                },
                actions = {
                    IconButton(
                        onClick = goBackTitleScreenFunction,
                        modifier = Modifier.testTag(PLAYERPROFILE_BACK_TITLESCREEN)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Go back to title screen",
                            tint = pokerText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pokerRed,
                    titleContentColor = pokerText
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(pokerGreen, pokerGreenDark))
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .background(Color(0xCC061F17), RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Player Information",
                    style = MaterialTheme.typography.headlineSmall,
                    color = pokerGold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileInfoRow("Username", playerData.username)
                ProfileInfoRow("Nome", playerData.name)
                ProfileInfoRow("Idade", playerData.age.toString())
                ProfileInfoRow("Crédito", "${playerData.credit} moedas")
                ProfileInfoRow("Vitórias", playerData.winCounter.toString())

                Spacer(modifier = Modifier.height(24.dp))

                // Depósito
                OutlinedTextField(
                    value = depositValue,
                    onValueChange = { depositValue = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Valor a depositar") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = pokerGreen,
                        unfocusedBorderColor = pokerGreen.copy(alpha = 0.7f),
                        focusedTextColor = pokerText,
                        unfocusedTextColor = pokerText,
                        focusedLabelColor = pokerGreen,
                        unfocusedLabelColor = pokerGreen
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val credit = depositValue.toIntOrNull() ?: 0
                        if (credit > 0) {
                            onDeposit(credit)
                            depositValue = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Depositar")
                }

                Spacer(modifier = Modifier.height(24.dp))
                Log.d("PlayerProfileView", "Invite code to copy: $inviteCode")

                // Convite
                if (inviteCode != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF061F17)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Teu Código de Convite:",
                                style = MaterialTheme.typography.labelLarge,
                                color = pokerText
                            )
                            Text(
                                text = inviteCode,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = pokerGold,
                                letterSpacing = 2.sp
                            )
                            Log.d("PlayerProfileView", "Invite code to copy: $inviteCode")
                            TextButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(inviteCode))
                                }
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Copiar Código", color = pokerGreen)
                            }
                        }
                    }
                }

                Button(
                    onClick = onGetInviteCode,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag(GET_INVITE_BUTTON_TAG),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    )
                ) {
                    Text("Get AppInvite", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.SemiBold,
            color = pokerText
        )
        Text(text = value, color = pokerText)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayerProfileViewPreview() {
    PlayerProfileView(
        playerData = User(
            id = 1,
            username = "renata1234",
            name = "Renata Castanheira",
            age = 19,
            credit = 100,
            winCounter = 5,
            lobbyId = null,
            passwordValidation = ""
        ),
        inviteCode = "CHELAS-1234",
        goBackTitleScreenFunction = {},
        onDeposit = {},
        onGetInviteCode = { }
    )
}
