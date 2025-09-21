package com.example.chimp.home

interface AboutService {
    fun getMembers(): List<Pair<String, String>>
    fun getEmails(): List<String>
    fun getGamePlayUrl(): String
}

class AboutServiceImpl : AboutService {
    override fun getMembers(): List<Pair<String, String>> {
            return listOf(
                "Diogo Arnauth" to "51634",
                "Renata Castanheira" to "51830",
                "Humberto Carvalho" to "50500"
            )

    }

    override fun getEmails(): List<String>{
        return listOf(
            "dioarnauth@gmail.com",
            "renataCastanheira@gmail.com",
            "humbertoCarvalho@gmail.com"
        )
    }

    override fun getGamePlayUrl(): String{
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }


}