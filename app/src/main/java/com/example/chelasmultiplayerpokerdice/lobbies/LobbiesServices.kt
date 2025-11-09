package com.example.chelasmultiplayerpokerdice.lobbies

import com.example.chelasmultiplayerpokerdice.domain.*
import kotlinx.coroutines.flow.Flow
import com.example.chelasmultiplayerpokerdice.mem.FakeDatabase

interface LobbiesService {
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