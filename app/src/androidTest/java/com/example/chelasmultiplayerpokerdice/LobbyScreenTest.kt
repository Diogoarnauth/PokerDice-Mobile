package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chelasmultiplayerpokerdice.lobbyScreen.LOBBYSCREEN_ABANDON_BUTTON
import com.example.chelasmultiplayerpokerdice.lobbyScreen.LOBBYSCREEN_LOBBY_INFO
import com.example.chelasmultiplayerpokerdice.lobbyScreen.LOBBYSCREEN_PLAYERS_LIST
import com.example.chelasmultiplayerpokerdice.lobbyScreen.LOBBYSCREEN_STARTGAME_BUTTON
import com.example.chelasmultiplayerpokerdice.lobbyScreen.Lobby
import com.example.chelasmultiplayerpokerdice.lobbyScreen.LobbyScreenView
import com.example.chelasmultiplayerpokerdice.lobbyScreen.Player
import org.junit.Rule
import org.junit.Test

class LobbyScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeLobby = Lobby(
        id = 1,
        name = "Poker Masters",
        owner = "Renata",
        description = "Lobby para testar a sorte 🎲",
        rounds = 12,
        isPrivate = false,
        password = null,
        playersCount = 3,
        maxPlayers = 4,
        players = listOf(
            Player(1, "Renata"),
            Player(2, "Diogo"),
            Player(3, "Humberto")
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
        composeTestRule.onNodeWithText("🌍 Público").assertIsDisplayed()
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
        fakeLobby.players.forEach { player ->
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
        val smallLobby = fakeLobby.copy(players = listOf(Player(1, "Renata")))
        composeTestRule.setContent {
            LobbyScreenView(
                lobby = smallLobby,
                onAbandon = {},
                onStartGame = {}
            )
        }

        composeTestRule.onNodeWithTag(LOBBYSCREEN_STARTGAME_BUTTON)
            .assertIsNotEnabled()
    }
}