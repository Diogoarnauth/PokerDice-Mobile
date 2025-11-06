package com.example.chelasmultiplayerpokerdice.lobbies

import com.example.chelasmultiplayerpokerdice.domain.*
import kotlinx.coroutines.flow.Flow
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase

//está apenas por ter, porque nao precisamos de nada implementado aqui, depois se precisarmos metemos
interface LobbiesService {
    //meter suspend fun
    fun getLobbies(): Flow<List<Lobby>>
    fun getGamePlayUrl(): String
}

class LobbiesFakeServiceImpl : LobbiesService {
    private val db = FakeDatabase
    override fun getLobbies(): Flow<List<Lobby>> {
        return db.lobbies
    }


    override fun getGamePlayUrl(): String {
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }
}