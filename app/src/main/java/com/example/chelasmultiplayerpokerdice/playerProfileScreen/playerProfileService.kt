package com.example.chelasmultiplayerpokerdice.playerProfileScreen

interface ProfileService {

    fun getPlayerProfileData(): List<PlayerProfileData>
    fun getPlayerName(): List<String>

    fun getPlayerUsername(): List<String>

    fun getPlayerAge(): List<Int>

}

class ProfileServiceImpl : ProfileService {

    override fun getPlayerName(): List<String> {
        return getPlayerProfileData().map { it.playerName }
    }

    override fun getPlayerUsername(): List<String> {
        return getPlayerProfileData().map { it.playerUsername }
    }

    override fun getPlayerAge(): List<Int> {
        return getPlayerProfileData().map { it.playerAge }
    }
    override fun getPlayerProfileData(): List<PlayerProfileData> {
        return listOf(
            PlayerProfileData("renata1234", "Renata Castanheira", 19),
            PlayerProfileData("diogoDaMota", "Diogo Arnauth", 20),
            PlayerProfileData("alpaca", "Humberto Carvalho", 21)
        )
    }

}
