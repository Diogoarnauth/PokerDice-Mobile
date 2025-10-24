package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.chelasmultiplayerpokerdice.lobbies.LOBBIES_BACK_TITLESCREEN
import com.example.chelasmultiplayerpokerdice.lobbies.LOBBIES_CREATE_BUTTON
import com.example.chelasmultiplayerpokerdice.lobbies.LOBBY_CARD_TAG
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesView
import com.example.chelasmultiplayerpokerdice.lobbies.Lobby
import com.example.chelasmultiplayerpokerdice.lobbies.Player
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

class LobbiesViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleLobbies = listOf(
        Lobby(
            0,
            "Poker Stars",
            "Renata",
            "jogo para aprender",
            5,
            false,
            null,
            9,
            emptyList<Player>()
        ),
        Lobby(1,"Lucky Dice", "Diogo", "jogo para intermedios", 6,true, "passSecreta",4, emptyList<Player>()),
        Lobby(2,"Chelas Crew", "Humberto", "jogo para pros",7, false, null,1, emptyList<Player>())
    )

    // --- TESTE 1 ---
    @Test
    fun showsListOfLobbiesCorrectly() {
        composeTestRule.setContent {
            LobbiesView(
                lobbies = sampleLobbies,
                goBackTitleScreenFunction = {},
                createLobbyFunction = {},
                selectLobbyFunction = {}
            )
        }

        // Verifica se o título principal está visível
        composeTestRule.onNodeWithText("🎲 Lobbies Disponíveis").assertIsDisplayed()

        // Verifica se os nomes dos lobbies aparecem
        composeTestRule.onNodeWithText("Poker Stars").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lucky Dice").assertIsDisplayed()
        composeTestRule.onNodeWithText("Chelas Crew").assertIsDisplayed()
    }

    // --- TESTE 2 ---
    @Test
    fun showsEmptyStateWhenNoLobbies() {
        composeTestRule.setContent {
            LobbiesView(
                lobbies = emptyList(),
                goBackTitleScreenFunction = {},
                createLobbyFunction = {},
                selectLobbyFunction = {}
            )
        }

        // Verifica se aparece a mensagem de lista vazia
        composeTestRule.onNodeWithText("Nenhum lobby disponível 👀").assertIsDisplayed()
    }

    // --- TESTE 3 ---
    @Test
    fun clickingHomeButtonCallsGoBackFunction() {
        var clicked = false

        composeTestRule.setContent {
            LobbiesView(
                lobbies = sampleLobbies,
                goBackTitleScreenFunction = { clicked = true },
                createLobbyFunction = {},
                selectLobbyFunction = {}
            )
        }

        composeTestRule.onNodeWithTag(LOBBIES_BACK_TITLESCREEN).performClick()
        assertTrue(clicked)
    }

    // --- TESTE 4 ---
    @Test
    fun clickingCreateButtonCallsCreateFunction() {
        var clicked = false

        composeTestRule.setContent {
            LobbiesView(
                lobbies = sampleLobbies,
                goBackTitleScreenFunction = {},
                createLobbyFunction = { clicked = true },
                selectLobbyFunction = {}
            )
        }

        composeTestRule.onNodeWithTag(LOBBIES_CREATE_BUTTON).performClick()
        assertTrue(clicked)
    }

    // --- TESTE 5 ---
    @Test
    fun clickingLobbyCardCallsSelectFunction() {
        var selectedLobbyId: Int? = null

        composeTestRule.setContent {
            LobbiesView(
                lobbies = sampleLobbies,
                goBackTitleScreenFunction = {},
                createLobbyFunction = {},
                selectLobbyFunction = { selectedLobbyId = it.id }
            )
        }

        // Clica no primeiro Card
        composeTestRule.onAllNodesWithTag(LOBBY_CARD_TAG)[0].performClick()

        assertEquals(0, selectedLobbyId)
    }
}

