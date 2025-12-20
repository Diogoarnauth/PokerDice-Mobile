package com.example.chelasmultiplayerpokerdice.lobbyCreation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

const val MAX_LOBBY_NAME_LENGTH = 15
const val MAX_DESCRIPTION_LENGTH = 40
const val MIN_CREDITS_ALLOWED = 10
const val MAX_CREDITS_ALLOWED = 1000000

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingLobbyCreationView() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Create Lobby") }) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialLobbyCreationView(
    goBackFunction: () -> Unit,
    onCreateLobby: (String, String, Int, Int, Int, Int) -> Unit
) {
    var lobbyName by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var maxPlayers by rememberSaveable { mutableIntStateOf(4) }
    var minPlayers by rememberSaveable { mutableIntStateOf(2) }

    // Gerimos os créditos como String para o TextField, convertendo para Int na validação
    var minCreditsText by rememberSaveable { mutableStateOf("10") }

    var numRounds by rememberSaveable { mutableIntStateOf(2) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Lobby") },
                navigationIcon = {
                    IconButton(
                        onClick = goBackFunction,
                        modifier = Modifier.testTag("BackButton")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = lobbyName,
                    onValueChange = { if (it.length <= MAX_LOBBY_NAME_LENGTH) lobbyName = it },
                    label = { Text("Lobby Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { if (it.length <= MAX_DESCRIPTION_LENGTH) description = it },
                    label = { Text("Short Description") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                // Campo de Créditos (Substituiu o Dropdown que causava crash)
                OutlinedTextField(
                    value = minCreditsText,
                    onValueChange = { newValue ->
                        // Apenas permite números
                        if (newValue.all { it.isDigit() }) {
                            minCreditsText = newValue
                        }
                    },
                    label = { Text("Min Credits ($MIN_CREDITS_ALLOWED - 1M)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).testTag("MinCreditsInput")
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Max Players:", modifier = Modifier.weight(1f))
                    DropdownMenuBox(
                        value = maxPlayers,
                        range = 3..8,
                        onValueChange = { maxPlayers = it },
                        modifier = Modifier.testTag("MaxPlayersDropdownButton")
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Min Players:", modifier = Modifier.weight(1f))
                    DropdownMenuBox(
                        value = minPlayers,
                        range = 2 until maxPlayers,
                        onValueChange = { minPlayers = it },
                        modifier = Modifier.testTag("MinPlayersDropdownButton")
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Rounds:", modifier = Modifier.weight(1f))
                    DropdownMenuBox(
                        value = numRounds,
                        range = 2..10,
                        onValueChange = { numRounds = it },
                        modifier = Modifier.testTag("RoundsDropdownButton")
                    )
                }

                error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        val creditsInt = minCreditsText.toIntOrNull() ?: 0

                        if (lobbyName.isBlank() || description.isBlank()) {
                            error = "Lobby name and description cannot be empty."
                        } else if (creditsInt < MIN_CREDITS_ALLOWED || creditsInt > MAX_CREDITS_ALLOWED) {
                            error = "Credits must be between $MIN_CREDITS_ALLOWED and 1,000,000."
                        } else {
                            error = null
                            onCreateLobby(lobbyName, description, minPlayers, maxPlayers, creditsInt, numRounds)
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag("CreateLobbyButton")
                ) {
                    Text("Start Match")
                }
            }
        }
    }
}

@Composable
fun DropdownMenuBox(
    value: Int,
    range: Iterable<Int>,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(
            onClick = { expanded = true },
            modifier = modifier
        ) { Text(value.toString()) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            range.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.toString()) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialLobbyCreationViewPreview() {
    InitialLobbyCreationView(goBackFunction = {}, onCreateLobby = { _, _, _, _, _, _ -> })
}