package com.example.chimp.home

interface TitleScreenService {
    fun getCreators(): List<String>

}

class TitleScreenServiceImpl : TitleScreenService {
    override fun getCreators(): List<String> {
        return listOf(
            "Diogo Arnauth",
            "Humberto Carvalho",
            "Renata Castanheira",
        )

    }

}