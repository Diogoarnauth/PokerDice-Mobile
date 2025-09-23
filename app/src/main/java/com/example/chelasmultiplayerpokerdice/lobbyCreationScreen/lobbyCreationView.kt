package com.example.chelasmultiplayerpokerdice.lobbyCreationScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview

const val MAX_LOBBY_NAME_LENGTH = 15
const val MAX_DESCRIPTION_LENGTH = 40

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingLobbyCreationView() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Create Lobby") }) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
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
    onCreateLobby: (String, String, Int, Int) -> Unit
) {
    var lobbyName by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var numPlayers by rememberSaveable { mutableIntStateOf(2) }
    var numRounds by rememberSaveable { mutableIntStateOf(2) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Lobby") },
                navigationIcon = { IconButton(onClick = goBackFunction) { Icon(Icons.Default.Edit, null) } }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
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
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { if (it.length <= MAX_DESCRIPTION_LENGTH) description = it },
                    label = { Text("Short Description") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Players:", modifier = Modifier.weight(1f))
                    DropdownMenuBox(
                        value = numPlayers,
                        range = 2..6,
                        onValueChange = {
                            numPlayers = it
                            if (numRounds % it != 0 || numRounds > 60) numRounds = it
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rounds:", modifier = Modifier.weight(1f))
                    DropdownMenuBox(
                        value = numRounds,
                        range = (numPlayers..60 step numPlayers).toList(),
                        onValueChange = { numRounds = it }
                    )
                }
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                Button(
                    onClick = {
                        if (lobbyName.isBlank() || description.isBlank()) {
                            error = "Lobby name and description cannot be empty."
                        } else {
                            error = null
                            onCreateLobby(lobbyName, description, numPlayers, numRounds)
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                ) {
                    Text("Start Match")
                }
            }
        }
    }
}

@Composable
fun DropdownMenuBox(value: Int, range: Iterable<Int>, onValueChange: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expanded = true }) { Text(value.toString()) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            for (item in range) {
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InitialLobbyCreationViewPreview() {
    InitialLobbyCreationView(
        goBackFunction = {},
        onCreateLobby = { _, _, _, _ -> }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingLobbyCreationViewPreview() {
        LoadingLobbyCreationView()
}
