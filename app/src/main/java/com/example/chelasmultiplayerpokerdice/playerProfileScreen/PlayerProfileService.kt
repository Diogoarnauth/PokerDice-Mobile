package com.example.chelasmultiplayerpokerdice.playerProfileScreen

interface ProfileService {

    fun getPlayerProfileData(): PlayerProfileData
    fun getPlayerName(): String

    fun getPlayerUsername(): String

    fun getPlayerAge(): Int

}

class ProfileServiceImpl : ProfileService {

    override fun getPlayerName(): String {
        return getPlayerProfileData().playerName
    }

    override fun getPlayerUsername(): String {
        return getPlayerProfileData().playerUsername
    }

    override fun getPlayerAge(): Int {
        return getPlayerProfileData().playerAge
    }
    override fun getPlayerProfileData(): PlayerProfileData {
        return PlayerProfileData("renata1234", "Renata Castanheira", 19)
    }


}