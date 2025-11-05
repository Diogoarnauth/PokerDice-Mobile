package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.PLAYERPROFILE_BACK_TITLESCREEN
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.PLAYERPROFILE_VIEW_TAG
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.PlayerProfileData
import com.example.chelasmultiplayerpokerdice.playerProfileScreen.PlayerProfileView
import org.junit.Rule
import org.junit.Test

class PlayerProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeData = PlayerProfileData(
        playerUsername = "renata1234",
        playerName = "Renata Castanheira",
        playerAge = 19
    )

    @Test
    fun playerProfileScreen_displaysPlayerDataCorrectly() {
        composeTestRule.setContent {
            PlayerProfileView(
                playerData = fakeData,
                goBackTitleScreenFunction = {}
            )
        }

        // 2. Verifica se o ecrã principal está visível
        composeTestRule.onNodeWithTag(PLAYERPROFILE_VIEW_TAG).assertIsDisplayed()

        // 3. Verifica os textos específicos
        composeTestRule.onNodeWithText("Username: ${fakeData.playerUsername}")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Nome: ${fakeData.playerName}")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Idade: ${fakeData.playerAge}")
            .assertIsDisplayed()
    }

    @Test
    fun playerProfileScreen_backButton_callsCallback() {
        // 4. Teste de Callback
        var backPressed = false
        composeTestRule.setContent {
            PlayerProfileView(
                playerData = fakeData,
                goBackTitleScreenFunction = { backPressed = true }
            )
        }

        // 5. Usa o TestTag para encontrar e clicar no botão
        composeTestRule.onNodeWithTag(PLAYERPROFILE_BACK_TITLESCREEN)
            .performClick()

        assert(backPressed)
    }
}