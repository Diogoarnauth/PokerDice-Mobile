package com.example.chelasmultiplayerpokerdice.game

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.chelasmultiplayerpokerdice.TAG
import com.example.chelasmultiplayerpokerdice.domain.Die
import com.example.chelasmultiplayerpokerdice.domain.DiceFace
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGold
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreen
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerGreenDark
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerRed
import com.example.chelasmultiplayerpokerdice.ui.theme.pokerText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameView(
    state: GameState,
    myUsername: String,
    onDieClicked: (Int) -> Unit,
    onRollClicked: () -> Unit,
    onRerollClicked: (List<Int>) -> Unit,
    onEndTurnClicked: () -> Unit
) {
    var dialogPlayer by rememberSaveable(state.players) { mutableStateOf<PlayerStatus?>(null) }
    Log.d(TAG, "GameView state = $state")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Round ${state.roundNumber}",
                        color = pokerText
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pokerRed,
                    titleContentColor = pokerText
                )
            )
        },
        bottomBar = {
            BottomStatusBar(
                currentPlayerName = state.currentPlayerName,
                rollsLeft = state.rollsLeft,
                backgroundColor = pokerRed,
                textColor = pokerText
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(pokerGreen, pokerGreenDark))
                )
        ) {
            MainGameArea(
                modifier = Modifier.weight(0.7f),
                state = state,
                myUsername = myUsername,
                onDieClicked = onDieClicked,
                onRollClicked = onRollClicked,
                onRerollClicked = onRerollClicked,
                onEndTurnClicked = onEndTurnClicked
            )
            PlayerListArea(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                players = state.players,
                onPlayerClicked = { player ->
                    dialogPlayer = player
                }
            )
        }

        if (dialogPlayer != null) {
            PlayerDiceDialog(
                player = dialogPlayer!!,
                onDismiss = { dialogPlayer = null }
            )
        }
    }
}

@Composable
fun MainGameArea(
    state: GameState,
    myUsername: String,
    modifier: Modifier = Modifier,
    onDieClicked: (Int) -> Unit,
    onRollClicked: () -> Unit,
    onRerollClicked: (List<Int>) -> Unit,
    onEndTurnClicked: () -> Unit
) {
    val isMyTurn = state.currentPlayerName == myUsername
    val isFirstRoll = state.rollsLeft == 3

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Current player",
                modifier = Modifier.size(80.dp),
                tint = if (isMyTurn) pokerGold else pokerText.copy(alpha = 0.7f)
            )
            Text(
                text = if (isMyTurn) "Your turn, $myUsername!" else "Turn: ${state.currentPlayerName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isMyTurn) FontWeight.Bold else FontWeight.Normal,
                color = pokerText
            )
            Text(
                text = "Rolls left: ${state.rollsLeft}",
                style = MaterialTheme.typography.bodySmall,
                color = pokerText.copy(alpha = 0.8f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        DiceRow(
            dice = state.dice,
            onDieClicked = if (isMyTurn) onDieClicked else { _ -> }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isMyTurn) {
            ButtonsRow(
                isFirstRoll = isFirstRoll,
                canRoll = state.canRoll,
                anyDieHeld = state.dice.any { it.isHeld },
                onRollClicked = onRollClicked,
                onRerollClicked = {
                    val selectedIds = state.dice.filter { it.isHeld }.map { it.id }
                    onRerollClicked(selectedIds)
                },
                onEndTurnClicked = onEndTurnClicked
            )
        } else {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xCC061F17)
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Waiting for ${state.currentPlayerName}...",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = pokerText
                )
            }
        }
    }
}

@Composable
fun DiceRow(dice: List<Die>, onDieClicked: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        dice.take(5).forEach { die ->
            DieView(die = die, onDieClicked = onDieClicked)
        }
    }
}

@Composable
fun DieView(die: Die, onDieClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .size(52.dp)
            .clickable { onDieClicked(die.id) },
        shape = RoundedCornerShape(8.dp),
        border = if (die.isHeld) BorderStroke(2.dp, pokerGold) else null,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF061F17)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = die.face.label,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = pokerText
            )
        }
    }
}

@Composable
fun ButtonsRow(
    isFirstRoll: Boolean,
    canRoll: Boolean,
    anyDieHeld: Boolean,
    onRollClicked: () -> Unit,
    onRerollClicked: () -> Unit,
    onEndTurnClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isFirstRoll) {
                Button(
                    onClick = onRollClicked,
                    enabled = canRoll,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    )
                ) {
                    Text("🎲 Roll")
                }
            } else {
                Button(
                    onClick = onRerollClicked,
                    enabled = canRoll && anyDieHeld,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pokerGold,
                        contentColor = pokerRed
                    )
                ) {
                    Text("🔁 Reroll")
                }
            }

            OutlinedButton(
                onClick = onEndTurnClicked,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = pokerText
                )
            ) {
                Text("⏭️ End Turn")
            }
        }

        if (!isFirstRoll && !anyDieHeld) {
            Text(
                "Select dice you want to change/keep",
                style = MaterialTheme.typography.labelSmall,
                color = pokerText.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun PlayerListArea(
    modifier: Modifier = Modifier,
    players: List<PlayerStatus>,
    onPlayerClicked: (PlayerStatus) -> Unit
) {
    Column(
        modifier = modifier
            .background(Color(0x33061F17))
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Players",
            style = MaterialTheme.typography.titleSmall,
            color = pokerText,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(players) { player ->
                PlayerCard(
                    player = player,
                    onPlayerClicked = onPlayerClicked
                )
            }
        }
    }
}

@Composable
fun PlayerCard(
    player: PlayerStatus,
    onPlayerClicked: (PlayerStatus) -> Unit
) {
    val alpha = if (player.isCurrentTurn) 0.4f else 1.0f
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha)
            .clickable { onPlayerClicked(player) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF061F17)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (player.isCurrentTurn) pokerGold else pokerText
            )
        }
    }
}

@Composable
fun PlayerDiceDialog(
    player: PlayerStatus,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Hand of ${player.name}")
        },
        text = {
            PlayerDiceRow(dice = player.dice)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        properties = DialogProperties(dismissOnClickOutside = true)
    )
}

@Composable
fun PlayerDiceRow(dice: List<Die>?) {
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Log.d(TAG, "Player dice list = $dice")
        if (dice == null) {
            repeat(5) {
                EmptyDieView()
            }
        } else {
            dice.forEach { die ->
                SmallDieView(die = die)
            }
        }
    }
}

@Composable
fun SmallDieView(die: Die) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color(0xFF061F17), RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = die.face.label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = pokerText
        )
    }
}

@Composable
fun EmptyDieView() {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                Color(0x33FFFFFF),
                RoundedCornerShape(4.dp)
            )
    )
}

@Composable
fun BottomStatusBar(
    currentPlayerName: String,
    rollsLeft: Int,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Turn: $currentPlayerName",
                color = textColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Rolls: $rollsLeft",
                color = textColor
            )
        }
    }
}

@Composable
fun RoundOverDialog(
    winnerName: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Round Finished!")
        },
        text = {
            Text("Round winner: $winnerName")
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Next Round")
            }
        },
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    )
}

@Composable
fun GameOverDialog(
    winnersNames: List<String>,
    onDismiss: () -> Unit
) {
    if (winnersNames.size == 1) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Game Over!")
            },
            text = {
                Text("Winner: ${winnersNames[0]}")
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Game Over!")
            },
            text = {
                Text("Winners: ${winnersNames.joinToString(", ")}")
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        )
    }
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameViewPreview() {
    val diogoDice = listOf(
        Die(id = 1, face = DiceFace.Ace, isHeld = false),
        Die(id = 2, face = DiceFace.Ten, isHeld = true),
        Die(id = 3, face = DiceFace.Ace, isHeld = false),
        Die(id = 4, face = DiceFace.Nine, isHeld = true),
        Die(id = 5, face = DiceFace.Jack, isHeld = false)
    )

    val renataDice = listOf(
        Die(id = 6, face = DiceFace.King, isHeld = true),
        Die(id = 7, face = DiceFace.King, isHeld = true),
        Die(id = 8, face = DiceFace.King, isHeld = true),
        Die(id = 9, face = DiceFace.Nine, isHeld = true),
        Die(id = 10, face = DiceFace.Nine, isHeld = true)
    )

    val previewState = GameState(
        id = 1,
        dice = diogoDice,
        players = listOf(
            PlayerStatus(id = 1, name = "Renata", dice = renataDice, hand = null, isCurrentTurn = false),
            PlayerStatus(id = 2, name = "Diogo", dice = null, hand = null, isCurrentTurn = true),
            PlayerStatus(id = 3, name = "Humberto", dice = null, hand = null, isCurrentTurn = false)
        ),
        currentPlayerName = "Diogo",
        rollsLeft = 2,
        roundNumber = 1,
        canRoll = true
    )

    GameView(
        state = previewState,
        myUsername = "Diogo",
        onDieClicked = {},
        onRollClicked = {},
        onRerollClicked = {},
        onEndTurnClicked = {}
    )
}

 */
