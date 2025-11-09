package com.example.chelasmultiplayerpokerdice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.example.chelasmultiplayerpokerdice.about.AboutNavigation
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.lobby.LobbyNavigation
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationNavigation
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesNavigation
import com.example.chelasmultiplayerpokerdice.login.LoginNavigation
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileNavigation
import com.example.chelasmultiplayerpokerdice.signup.SignupNavigation
import com.example.chelasmultiplayerpokerdice.title.TitleNavigation

const val LOBBY_ID_EXTRA = "LOBBY_ID"
const val AUTHENTICATED_USER_EXTRA = "AUTHENTICATED_USER"

@SuppressLint("RestrictedApi")
class NavigationIntentImpl(private val context: Context) :
    TitleNavigation, AboutNavigation, PlayerProfileNavigation,
    LobbiesNavigation, LobbyNavigation, LobbyCreationNavigation,
    SignupNavigation, LoginNavigation {

    private fun createIntent(
        clazz: Class<*>,
        user: AuthenticatedUser,
        clearStack: Boolean = true,
        lobbyId: Int? = null
    ): Intent {
        val intent = Intent(context, clazz).apply {
            if (clearStack) {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }

            putExtra(AUTHENTICATED_USER_EXTRA, user)

            if (lobbyId != null) {
                putExtra(LOBBY_ID_EXTRA, lobbyId)
            }
        }
        return intent
    }


    override fun goToTitleScreen(user: AuthenticatedUser) {
        val intent = createIntent(TitleActivity::class.java, user, clearStack = true)
        context.startActivity(intent)
    }

    override fun goToLobbiesScreen(user: AuthenticatedUser) {
        val intent = createIntent(LobbiesActivity::class.java, user)
        context.startActivity(intent)
    }

    override fun goToPlayerProfileScreen(user: AuthenticatedUser) {
        val intent = createIntent(PlayerProfileActivity::class.java, user)
        context.startActivity(intent)
    }

    override fun goToAboutScreen(user: AuthenticatedUser) {
        val intent = createIntent(AboutActivity::class.java, user)
        context.startActivity(intent)
    }

    override fun goToLobbyDetailsScreen(user: AuthenticatedUser, lobbyId: Int) {
        val intent = createIntent(LobbyActivity::class.java, user, clearStack = false, lobbyId = lobbyId)
        context.startActivity(intent)
    }

    override fun goToLobbyCreationScreen(user: AuthenticatedUser) {
        val intent = createIntent(LobbyCreationActivity::class.java, user, clearStack = false)
        context.startActivity(intent)
    }

    override fun goToGameScreen() {
        TODO("Not yet implemented")
    }

    override fun goToTitleScreen() {
        val intent = Intent(context, TitleActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }

    override fun goToSignupScreen() {
        val intent = Intent(context, SignupActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToLoginScreen() {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}