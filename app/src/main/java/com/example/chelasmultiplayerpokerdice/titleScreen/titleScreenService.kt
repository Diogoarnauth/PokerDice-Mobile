package com.example.chimp.home

interface TitleScreenService {
    fun getCreators(): List<String>
    fun getStartMatch(): () -> Unit
    fun getProfile(): () -> Unit
    fun getAboutFunction(): () -> Unit
}

class TitleScreenServiceImpl : TitleScreenService {
    override fun getCreators(): List<String> {
        return listOf(
            "Diogo Arnauth",
            "Humberto Carvalho",
            "Renata Castanheira",
        )

    }

    override fun getStartMatch()= {}

    override fun getProfile()= {}

    override fun getAboutFunction()= {}
}