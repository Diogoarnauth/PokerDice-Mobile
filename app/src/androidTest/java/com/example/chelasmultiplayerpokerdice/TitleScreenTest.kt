package com.example.chelasmultiplayerpokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chelasmultiplayerpokerdice.titleScreen.TITLESCREEN_ABOUT_BUTTON
import com.example.chelasmultiplayerpokerdice.titleScreen.TITLESCREEN_PROFILE_BUTTON
import com.example.chelasmultiplayerpokerdice.titleScreen.TITLESCREEN_STARTMATCH_BUTTON
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreen
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreenService
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleScreenNavigation
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

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
            composeTestRule.onNodeWithText(creator, useUnmergedTree = true).assertIsDisplayed()
        }
    }

    // --- TESTE 2: botão "Iniciar Partida" chama goToLobbiesScreen ---
    @Test
    fun titleScreen_startMatchButton_triggersNavigationFunction() {
        var startClicked = false

        val fakeNavigation = object : TitleScreenNavigation {
            override fun goToPlayerProfileScreen() {}
            override fun goToAboutScreen() {}
            override fun goToLobbiesScreen() { startClicked = true }
        }

        composeTestRule.setContent {
            TitleScreen(service = fakeService, navigator = fakeNavigation)
        }

        composeTestRule.onNodeWithTag(TITLESCREEN_STARTMATCH_BUTTON).performClick()
        //composeTestRule.onNodeWithText("Iniciar Partida", useUnmergedTree = true).performClick()
        assertTrue("O botão 'Iniciar Partida' não chamou a função esperada", startClicked)
    }

    // --- TESTE 3: botão "Perfil" chama goToPlayerProfileScreen ---
    @Test
    fun titleScreen_profileButton_triggersNavigationFunction() {
        var profileClicked = false

        val fakeNavigation = object : TitleScreenNavigation {
            override fun goToPlayerProfileScreen() { profileClicked = true }
            override fun goToAboutScreen() {}
            override fun goToLobbiesScreen() {}
        }

        composeTestRule.setContent {
            TitleScreen(service = fakeService, navigator = fakeNavigation)
        }

        composeTestRule.onNodeWithTag(TITLESCREEN_PROFILE_BUTTON).performClick()


        assertTrue("O botão 'Perfil' não chamou a função esperada", profileClicked)
    }

    // --- TESTE 4: botão "Sobre" chama goToAboutScreen ---
    @Test
    fun titleScreen_aboutButton_triggersNavigationFunction() {
        var aboutClicked = false

        val fakeNavigation = object : TitleScreenNavigation {
            override fun goToPlayerProfileScreen() {}
            override fun goToAboutScreen() { aboutClicked = true }
            override fun goToLobbiesScreen() {}
        }

        composeTestRule.setContent {
            TitleScreen(service = fakeService, navigator = fakeNavigation)
        }

        composeTestRule.onNodeWithTag(TITLESCREEN_ABOUT_BUTTON).performClick()

        assertTrue("O botão 'Sobre' não chamou a função esperada", aboutClicked)
    }
}