package com.example.chelasmultiplayerpokerdice.playerProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

const val PLAYERPROFILE_BACK_TITLESCREEN = "User Profile back to title screen"
const val PLAYERPROFILE_VIEW_TAG = "User Profile View"
const val GET_INVITE_BUTTON_TAG = "Get Invite Button"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProfileView(
    playerData: User,
    inviteCode: String? = null, // Novo: Estado do código recebido
    goBackTitleScreenFunction: () -> Unit,
    onGetInviteCode: () -> Unit // Novo: Ação de clique
) {

    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(PLAYERPROFILE_VIEW_TAG),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "👤 Perfil de ${playerData.username}",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = goBackTitleScreenFunction,
                        modifier = Modifier.testTag(PLAYERPROFILE_BACK_TITLESCREEN)
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Go back to title screen")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Top, // Mudado para Top para o botão não fugir
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Informações do Jogador",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Dados do Jogador
                ProfileInfoRow("Username", playerData.username)
                ProfileInfoRow("Nome", playerData.name)
                ProfileInfoRow("Idade", playerData.age.toString())
                ProfileInfoRow("Crédito", "${playerData.credit} moedas")
                ProfileInfoRow("Vitórias", playerData.winCounter.toString())

                Spacer(modifier = Modifier.height(40.dp))

                // Zona do Convite
                if (inviteCode != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Teu Código de Convite:", style = MaterialTheme.typography.labelLarge)
                            Text(
                                text = inviteCode,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                letterSpacing = 2.sp
                            )
                            TextButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(inviteCode))
                                }
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Copiar Código")
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
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Get AppInvite", fontSize = 16.sp)
                }
            }
        }
    )
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.SemiBold)
        Text(text = value)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayerProfileViewPreview() {
    PlayerProfileView(
        playerData = User(1, "renata123", "Renata C", "renata",19, 100, 5, null),
        inviteCode = "ABC-123",
        goBackTitleScreenFunction = {},
        onGetInviteCode = {}
    )
}