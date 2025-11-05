package com.example.chelasmultiplayerpokerdice


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.chelasmultiplayerpokerdice.about.ABOUTSCREEN_EMAIL_BUTTON
import com.example.chelasmultiplayerpokerdice.about.ABOUTSCREEN_WEB_LINK
import com.example.chelasmultiplayerpokerdice.about.AboutScreenView
import com.example.chelasmultiplayerpokerdice.about.Author
import com.example.chelasmultiplayerpokerdice.about.TITLESCREEN_BUTTON
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class AboutScreenViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleMembers = listOf(
        Author("Diogo Arnauth", 51634, "dioarnauth@gmail.com"),
        Author("Renata Castanheira", 51830, "renataCastanheira@gmail.com"),
        Author("Humberto Carvalho", 50500, "betocp@sapo.pt")
    )

    // --- TESTE 1 ---
    @Test
    fun showsMainTextsCorrectly() {
        composeTestRule.setContent {
            AboutScreenView(
                members = sampleMembers,
                gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice",
                titleScreenFunction = {}
            )
        }

        // Verifica o título
        composeTestRule.onNodeWithText("ℹ️ Sobre o Jogo").assertIsDisplayed()

        // Verifica a secção “Membros do Grupo”
        composeTestRule.onNodeWithText("Membros do Grupo").assertIsDisplayed()

        // Verifica se os nomes dos membros aparecem
        composeTestRule.onNodeWithText("- Diogo Arnauth (51634)").assertIsDisplayed()
        composeTestRule.onNodeWithText("- Renata Castanheira (51830)").assertIsDisplayed()
        composeTestRule.onNodeWithText("- Humberto Carvalho (50500)").assertIsDisplayed()
    }

    // --- TESTE 2 ---
    @Test
    fun clickingHomeButtonCallsTitleScreenFunction() {
        var clicked = false

        composeTestRule.setContent {
            AboutScreenView(
                members = sampleMembers,
                gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice",
                titleScreenFunction = { clicked = true }
            )
        }

        composeTestRule.onNodeWithTag(TITLESCREEN_BUTTON).performClick()
        assertTrue(clicked)
    }

    // --- TESTE 3 ---
    @Test
    fun showsGameplayUrlLink() {
        composeTestRule.setContent {
            AboutScreenView(
                members = sampleMembers,
                gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice",
                titleScreenFunction = {}
            )
        }

        composeTestRule.onNodeWithTag(ABOUTSCREEN_WEB_LINK).assertIsDisplayed()
        composeTestRule.onNodeWithText("Ver descrição completa").assertIsDisplayed()
    }

    // --- TESTE 4 ---
    @Test
    fun showsEmailButton() {
        composeTestRule.setContent {
            AboutScreenView(
                members = sampleMembers,
                gameplayUrl = "https://en.wikipedia.org/wiki/Poker_dice",
                titleScreenFunction = {}
            )
        }

        composeTestRule.onNodeWithTag(ABOUTSCREEN_EMAIL_BUTTON).assertIsDisplayed()
        composeTestRule.onNodeWithText("Contactar todos por email").assertIsDisplayed()
    }
}
