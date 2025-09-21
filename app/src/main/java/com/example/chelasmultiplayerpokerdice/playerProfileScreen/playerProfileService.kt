package com.example.chimp.home

interface ProfileService {
    fun getPlayerName(): String

    fun getPlayerUsername(): String

    fun getPlayerAge(): Int

}

class ProfileServiceImpl : ProfileService {

    override fun getPlayerUsername(): String {
        return "Jogador1"
    }
    override fun getPlayerName(): String{
        return "Mário"
    }
    override fun getPlayerAge():Int{
        return 18
    }


}