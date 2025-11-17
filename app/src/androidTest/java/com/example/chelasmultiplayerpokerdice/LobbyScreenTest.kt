package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chelasmultiplayerpokerdice.lobby.LOBBYSCREEN_ABANDON_BUTTON
import com.example.chelasmultiplayerpokerdice.lobby.LOBBYSCREEN_LOBBY_INFO
import com.example.chelasmultiplayerpokerdice.lobby.LOBBYSCREEN_PLAYERS_LIST
import com.example.chelasmultiplayerpokerdice.lobby.LOBBYSCREEN_STARTGAME_BUTTON
import com.example.chelasmultiplayerpokerdice.domain.*
import com.example.chelasmultiplayerpokerdice.lobby.LobbyScreenView
import org.junit.Rule
import org.junit.Test

class LobbyScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
/*
    private val fakeLobby = Lobby(
        id = 1,
        name = "Poker Masters",
        description = "Lobby para testar a sorte 🎲",
        hostId = 1,
        minUsers = 2,
        maxUsers = 4,
        rounds = 12,
        minCreditToParticipate = 0,
        playersCount = 3,
        users = listOf(
            User(1, "RenataDoGrau", "PasswordFraca", "Renata", 18, 100, 0, 1),
            User(2, "DiogoDoGrau", "PasswordFraca", "Diogo", 18, 100, 0, 1),
            User(3, "BertoDoGrau", "PasswordFraca", "Humberto", 18, 100, 0, 1),
        )
    )


    @Test
    fun lobbyScreen_displaysLobbyInformationCorrectly() {
        composeTestRule.setContent {
            LobbyScreenView(
                lobby = fakeLobby,
                onAbandon = {},
                onStartGame = {}
            )
        }

        // Verifica se o bloco do lobby info está visível
        composeTestRule.onNodeWithTag(LOBBYSCREEN_LOBBY_INFO).assertIsDisplayed()

        // Verifica os textos específicos dentro do lobby
        composeTestRule.onNodeWithText("Lobby para testar a sorte 🎲").assertIsDisplayed()
        composeTestRule.onNodeWithText("👑 Dono: Renata").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jogadores: 3/4").assertIsDisplayed()
        composeTestRule.onNodeWithText("Número de rondas: 12").assertIsDisplayed()
    }

    @Test
    fun lobbyScreen_displaysPlayersListCorrectly() {
        composeTestRule.setContent {
            LobbyScreenView(
                lobby = fakeLobby,
                onAbandon = {},
                onStartGame = {}
            )
        }

        // Verifica se o bloco da lista de jogadores está visível
        val playersListNode = composeTestRule.onNodeWithTag(LOBBYSCREEN_PLAYERS_LIST)
        playersListNode.assertIsDisplayed()

        // Verifica cada jogador pelo texto
        fakeLobby.users.forEach { player ->
            composeTestRule.onNodeWithText(player.name).assertIsDisplayed()
            composeTestRule.onNodeWithText("ID: ${player.id}").assertIsDisplayed()
        }
    }

    @Test
    fun lobbyScreen_abandonButton_callsCallback() {
        var abandoned = false
        composeTestRule.setContent {
            LobbyScreenView(
                lobby = fakeLobby,
                onAbandon = { abandoned = true },
                onStartGame = {}
            )
        }

        composeTestRule.onNodeWithTag(LOBBYSCREEN_ABANDON_BUTTON)
            .performClick()

        assert(abandoned)
    }

    @Test
    fun lobbyScreen_startGameButton_callsCallback() {
        var started = false
        composeTestRule.setContent {
            LobbyScreenView(
                lobby = fakeLobby,
                onAbandon = {},
                onStartGame = { started = true }
            )
        }

        composeTestRule.onNodeWithTag(LOBBYSCREEN_STARTGAME_BUTTON)
            .performClick()

        assert(started)
    }

    @Test
    fun lobbyScreen_startGameButton_disabledIfNotEnoughPlayers() {
        val smallLobby = fakeLobby.copy(users = listOf(User(1, "RenataDoGrau", "PasswordFraca", "Renata", 18, 100, 0, 1)))
        composeTestRule.setContent {
            LobbyScreenView(
                lobby = smallLobby,
                onAbandon = {},
                onStartGame = {}
            )
        }

        composeTestRule.onNodeWithTag(LOBBYSCREEN_STARTGAME_BUTTON)
            .assertIsNotEnabled()
    }*/
}