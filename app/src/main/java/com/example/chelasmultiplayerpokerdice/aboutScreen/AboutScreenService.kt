package com.example.chelasmultiplayerpokerdice.aboutScreen

interface AboutService {
    fun getMembers(): List<Author>
    fun getGamePlayUrl(): String
}

class AboutServiceImpl : AboutService {
    override fun getMembers(): List<Author> {
        return listOf(
            Author("Diogo Arnauth", 51634, "dioarnauth@gmail.com"),
            Author("Renata Castanheira", 51830, "renataCatanheira@gmail.com"), // catanheira ou caStanheira?
            Author("Humberto Carvalho", 50500, "betocp@sapo.pt")
        )
    }

    override fun getGamePlayUrl(): String{
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }


}