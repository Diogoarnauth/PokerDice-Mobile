package com.example.chelasmultiplayerpokerdice.title

interface TitleService {
    fun getCreators(): List<String>

}

class TitleFakeServiceImpl : TitleService {
    override fun getCreators(): List<String> {
        return listOf(
            "Diogo Arnauth",
            "Humberto Carvalho",
            "Renata Castanheira",
        )

    }

}