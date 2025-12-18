package com.example.chelasmultiplayerpokerdice

import android.app.Application
import com.example.chelasmultiplayerpokerdice.about.AboutFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.about.AboutService
import com.example.chelasmultiplayerpokerdice.auth.AuthInfoRepo
import com.example.chelasmultiplayerpokerdice.auth.InMemoryAuthRepo
import com.example.chelasmultiplayerpokerdice.auth.login.LoginService
import com.example.chelasmultiplayerpokerdice.auth.login.LoginServiceImpl
import com.example.chelasmultiplayerpokerdice.auth.signup.SignupService
import com.example.chelasmultiplayerpokerdice.auth.signup.SignupServiceImpl
import com.example.chelasmultiplayerpokerdice.domain.remote.models.KtorClient
import com.example.chelasmultiplayerpokerdice.game.GameFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.game.GameRepository
import com.example.chelasmultiplayerpokerdice.game.GameService
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesRepository
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesService
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesServiceImpl
import com.example.chelasmultiplayerpokerdice.lobby.LobbyRepository
import com.example.chelasmultiplayerpokerdice.lobby.LobbyService
import com.example.chelasmultiplayerpokerdice.lobby.LobbyServiceImpl
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobbyCreation.LobbyCreationService
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileService
import com.example.chelasmultiplayerpokerdice.title.TitleFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.title.TitleService
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger
const val TAG = "PokerDice"

const val BASE_URL = "http://localhost:8080/api"

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

    private val client by lazy {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KTOR_HTTP", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }



    override val authRepo: AuthInfoRepo by lazy {
        InMemoryAuthRepo()
    }

    override val gameRepository: GameRepository by lazy {
        GameRepository(gameService)
    }

    override val lobbyRepository: LobbyRepository by lazy {
        LobbyRepository(lobbyService)
    }

    override val lobbiesRepository: LobbiesRepository by lazy {
        LobbiesRepository(lobbiesService)
    }

    override val loginService: LoginService by lazy {
        LoginServiceImpl(client)
    }

    override val signupService: SignupService by lazy {
        SignupServiceImpl(client)
    }

    override val lobbiesService: LobbiesService by lazy {
        LobbiesServiceImpl(client)
    }

    override val lobbyService: LobbyService by lazy {
        LobbyServiceImpl(client)
    }

    override val aboutService: AboutService by lazy {
        AboutFakeServiceImpl()
    }

    override val gameService: GameService by lazy {
        GameFakeServiceImpl()
    }


    override val lobbyCreationService: LobbyCreationService by lazy {
        LobbyCreationFakeServiceImpl()
    }

    override val playerProfileService: PlayerProfileService by lazy {
        PlayerProfileFakeServiceImpl()
    }

    override val titleService: TitleService by lazy {
        TitleFakeServiceImpl()
    }

}