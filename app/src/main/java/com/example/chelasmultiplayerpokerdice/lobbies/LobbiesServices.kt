package com.example.chelasmultiplayerpokerdice.lobbies

import com.example.chelasmultiplayerpokerdice.domain.*

//está apenas por ter, porque nao precisamos de nada implementado aqui, depois se precisarmos metemos
interface LobbiesService {
    //meter suspend fun
    fun getLobbies(): List<Lobby>
    fun getGamePlayUrl(): String
}

class LobbiesFakeServiceImpl : LobbiesService {
    override fun getLobbies(): List<Lobby> {
        return listOf(
            Lobby(
                id = 0,
                name = "Poker Stars",
                description = "jogo para aprender",
                hostId = 1,
                minUsers = 2,
                maxUsers = 10,
                rounds = 5,
                minCreditToParticipate = 0,
                playersCount = 9,
                players = emptyList()
            ),
            Lobby(
                id = 1,
                name = "Lucky Dice",
                description = "jogo para intermedios",
                hostId = 2,
                minUsers = 2,
                maxUsers = 10,
                rounds = 6,
                minCreditToParticipate = 0,
                playersCount = 4,
                players = emptyList()
            ),
            Lobby(
                id = 2,
                name = "Chelas Crew",
                description = "jogo para pros",
                hostId = 3,
                minUsers = 2,
                maxUsers = 10,
                rounds = 7,
                minCreditToParticipate = 0,
                playersCount = 1,
                players = emptyList()
            )
        )
    }

    override fun getGamePlayUrl(): String {
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }


}