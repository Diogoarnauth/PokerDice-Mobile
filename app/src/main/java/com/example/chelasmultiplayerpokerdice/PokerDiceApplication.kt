package com.example.chelasmultiplayerpokerdice

import android.app.Application
import com.example.chelasmultiplayerpokerdice.about.AboutFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.about.AboutService
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesService
import com.example.chelasmultiplayerpokerdice.lobby.LobbyFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobby.LobbyService
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationService
/*
import com.example.chelasmultiplayerpokerdice.login.LoginFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.login.LoginService
*/
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileService
import com.example.chelasmultiplayerpokerdice.title.TitleFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.title.TitleService

const val TAG = "PokerDice"


interface DependenciesContainer {
    val aboutService: AboutService
    val lobbiesService: LobbiesService
    val lobbyService: LobbyService
    val lobbyCreationService: LobbyCreationService
    //val loginService: LoginService
    val playerProfileService: PlayerProfileService
    val titleService: TitleService
}


class PokerDiceApplication : Application(), DependenciesContainer {

    override val aboutService: AboutService by lazy {
        AboutFakeServiceImpl()
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

    /*
        val loginService: LoginService by lazy {
            LoginFakeServiceImpl()
        }
    */
    override val playerProfileService: PlayerProfileService by lazy {
        PlayerProfileFakeServiceImpl()
    }

    override val titleService: TitleService by lazy {
        TitleFakeServiceImpl()
    }

}