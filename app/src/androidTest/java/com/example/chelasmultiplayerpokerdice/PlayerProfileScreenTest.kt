package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chelasmultiplayerpokerdice.domain.User
import com.example.chelasmultiplayerpokerdice.playerProfile.PLAYERPROFILE_BACK_TITLESCREEN
import com.example.chelasmultiplayerpokerdice.playerProfile.PLAYERPROFILE_VIEW_TAG
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileView
import org.junit.Rule
import org.junit.Test

class PlayerProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeData = User(
        1,
        "RenataDoGrau",
        "PasswordFraca",
        "Renata",
        18,
        100,
        0,
        1
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
        composeTestRule.onNodeWithText("Username: ${fakeData.username}")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Nome: ${fakeData.name}")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Idade: ${fakeData.age}")
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