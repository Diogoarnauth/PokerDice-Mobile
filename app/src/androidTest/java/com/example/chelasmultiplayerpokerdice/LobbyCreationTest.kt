package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.chelasmultiplayerpokerdice.lobbyCreation.InitialLobbyCreationView
import org.junit.Rule
import org.junit.Test

class LobbyCreationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lobbyCreation_backButton_callsCallback() {
        var backPressed = false
        composeTestRule.setContent {
            InitialLobbyCreationView(
                goBackFunction = { backPressed = true },
                onCreateLobby = { _, _, _, _ -> }
            )
        }

        composeTestRule.onNodeWithTag("BackButton")
            .performClick()

        assert(backPressed)
    }

    @Test
    fun lobbyCreation_createButton_disabledIfFieldsAreEmpty() {
        composeTestRule.setContent {
            InitialLobbyCreationView(
                goBackFunction = {},
                onCreateLobby = { _, _, _, _ -> }
            )
        }

        // Verifica que o botão está ativo ao clicar
        val createButton = composeTestRule.onNodeWithTag("CreateLobbyButton")
        createButton.assertIsEnabled()

        // Clica sem preencher
        createButton.performClick()

        // Verifica que a mensagem de erro aparece
        composeTestRule.onNodeWithText("Lobby name and description cannot be empty.")
            .assertIsDisplayed()
    }

    @Test
    fun lobbyCreation_createLobby_callsCallback_withValidData() {
        // Variáveis Flag para o callback
        var createCalled = false
        var lobbyName = ""
        var lobbyDesc = ""
        var numPlayers = 0
        var numRounds = 0

        composeTestRule.setContent {
            InitialLobbyCreationView(
                goBackFunction = {},
                onCreateLobby = { name, desc, players, rounds ->
                    createCalled = true
                    lobbyName = name
                    lobbyDesc = desc
                    numPlayers = players
                    numRounds = rounds
                }
            )
        }

        // Interage com os TextFields
        composeTestRule.onNodeWithText("Lobby Name")
            .performTextInput("My Test Lobby")

        composeTestRule.onNodeWithText("Short Description")
            .performTextInput("A cool description")

        // Interage com os Dropdowns

        // Seleciona 4 jogadores
        composeTestRule.onNodeWithTag("PlayersDropdownButton").performClick()
        composeTestRule.onNodeWithText("4").performClick() // Clica no item do menu

        // Seleciona 8 rondas
        composeTestRule.onNodeWithTag("RoundsDropdownButton").performClick()
        composeTestRule.onNodeWithText("8").performClick() // Clica no item do menu

        // Clica no botão de criar
        composeTestRule.onNodeWithTag("CreateLobbyButton").performClick()

        // Verifica os callbacks
        assert(createCalled)
        assert(lobbyName == "My Test Lobby")
        assert(lobbyDesc == "A cool description")
        assert(numPlayers == 4)
        assert(numRounds == 8)
    }
}