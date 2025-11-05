// kotlin
package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chelasmultiplayerpokerdice.title.TITLESCREEN_ABOUT_BUTTON
import com.example.chelasmultiplayerpokerdice.title.TITLESCREEN_PROFILE_BUTTON
import com.example.chelasmultiplayerpokerdice.title.TITLESCREEN_STARTMATCH_BUTTON
import com.example.chelasmultiplayerpokerdice.title.TitleScreenView
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class TitleScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleCreators = listOf(
        "Diogo Arnauth",
        "Humberto Carvalho",
        "Renata Castanheira"
    )

    @Test
    fun titleScreen_displaysCreatorsCorrectly() {
        composeTestRule.setContent {
            TitleScreenView(
                creators = sampleCreators,
                startMatchFunction = {},
                profileFunction = {},
                aboutFunction = {}
            )
        }

        sampleCreators.forEach { creator ->
            composeTestRule.onNodeWithText(creator).assertIsDisplayed()
        }
    }

    @Test
    fun titleScreen_startMatchButton_triggersNavigationFunction() {
        var startClicked = false

        composeTestRule.setContent {
            TitleScreenView(
                creators = sampleCreators,
                startMatchFunction = { startClicked = true },
                profileFunction = {},
                aboutFunction = {}
            )
        }

        composeTestRule.onNodeWithTag(TITLESCREEN_STARTMATCH_BUTTON).performClick()
        assertTrue(startClicked)
    }

    @Test
    fun titleScreen_profileButton_triggersNavigationFunction() {
        var profileClicked = false

        composeTestRule.setContent {
            TitleScreenView(
                creators = sampleCreators,
                startMatchFunction = {},
                profileFunction = { profileClicked = true },
                aboutFunction = {}
            )
        }

        composeTestRule.onNodeWithTag(TITLESCREEN_PROFILE_BUTTON).performClick()
        assertTrue(profileClicked)
    }

    @Test
    fun titleScreen_aboutButton_triggersNavigationFunction() {
        var aboutClicked = false

        composeTestRule.setContent {
            TitleScreenView(
                creators = sampleCreators,
                startMatchFunction = {},
                profileFunction = {},
                aboutFunction = { aboutClicked = true }
            )
        }

        composeTestRule.onNodeWithTag(TITLESCREEN_ABOUT_BUTTON).performClick()
        assertTrue(aboutClicked)
    }
}
