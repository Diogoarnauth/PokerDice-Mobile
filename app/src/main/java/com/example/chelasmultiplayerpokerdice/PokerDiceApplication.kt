package com.example.chelasmultiplayerpokerdice

import android.app.Application
import com.example.chelasmultiplayerpokerdice.about.AboutFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.about.AboutService
import com.example.chelasmultiplayerpokerdice.auth.AuthInfoRepo
import com.example.chelasmultiplayerpokerdice.auth.InMemoryAuthRepo
import com.example.chelasmultiplayerpokerdice.game.GameFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.game.GameService
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesService
import com.example.chelasmultiplayerpokerdice.lobby.LobbyFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobby.LobbyService
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationService
import com.example.chelasmultiplayerpokerdice.auth.login.LoginFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.auth.login.LoginService
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileService
import com.example.chelasmultiplayerpokerdice.auth.signup.SignupFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.auth.signup.SignupService
import com.example.chelasmultiplayerpokerdice.title.TitleFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.title.TitleService
import com.example.chelasmultiplayerpokerdice.game.GameRepository
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesRepository
import com.example.chelasmultiplayerpokerdice.lobby.LobbyRepository

const val TAG = "PokerDice"


interface DependenciesContainer {
    val aboutService: AboutService
    val lobbiesService: LobbiesService
    val lobbyService: LobbyService
    val lobbyCreationService: LobbyCreationService
    val loginService: LoginService
    val playerProfileService: PlayerProfileService
    val titleService: TitleService
    val signupService: SignupService
    val gameService: GameService
    val authRepo: AuthInfoRepo
    val lobbyRepository: LobbyRepository
    val lobbiesRepository: LobbiesRepository
    val gameRepository: GameRepository
}


class PokerDiceApplication : Application(), DependenciesContainer {

    override val aboutService: AboutService by lazy {
        AboutFakeServiceImpl()
    }

    override val gameService: GameService by lazy {
        GameFakeServiceImpl()
    }

    override val gameRepository: GameRepository by lazy {
        GameRepository(gameService)
    }

    override val lobbyRepository: LobbyRepository by lazy {
        LobbyRepository(lobbyService)
    }

    override val authRepo: AuthInfoRepo by lazy {
        InMemoryAuthRepo()
    }

    override val lobbiesRepository: LobbiesRepository by lazy {
        LobbiesRepository(lobbiesService)
    }

    override val lobbiesService: LobbiesService by lazy {
        LobbiesFakeServiceImpl()
    }

    override val lobbyService: LobbyService by lazy {
        LobbyFakeServiceImpl()
    }

    override val lobbyCreationService: LobbyCreationService by lazy {
        LobbyCreationFakeServiceImpl()
    }

    override val loginService: LoginService by lazy {
        LoginFakeServiceImpl()
    }

    override val signupService: SignupService by lazy {
        SignupFakeServiceImpl()
    }

    override val playerProfileService: PlayerProfileService by lazy {
        PlayerProfileFakeServiceImpl()
    }

    override val titleService: TitleService by lazy {
        TitleFakeServiceImpl()
    }

}