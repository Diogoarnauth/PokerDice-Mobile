package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreen
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreenService
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreenNavigation
import org.junit.Rule
import org.junit.Test

class TitleScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Fake service que devolve uma lista de criadores
    private val fakeService = object : TitleScreenService {
        override fun getCreators(): List<String> {
            return listOf("Diogo Arnauth", "Humberto Carvalho", "Renata Castanheira")
        }
    }

    // --- TESTE 1: verifica se os criadores aparecem ---
    @Test
    fun titleScreen_displaysCreatorsCorrectly() {
        val fakeNavigation = object : TitleScreenNavigation {
            override fun goToPlayerProfileScreen() {}
            override fun goToAboutScreen() {}
            override fun goToLobbiesScreen() {}
        }

        composeTestRule.setContent {
            TitleScreen(service = fakeService, navigator = fakeNavigation)
        }

        fakeService.getCreators().forEach { creator ->
            composeTestRule.onNodeWithText(creator).assertIsDisplayed()
        }
    }
}