package com.example.chelasmultiplayerpokerdice.lobbies

//está apenas por ter, porque nao precisamos de nada implementado aqui, depois se precisarmos metemos
interface LobbiesService {//meter suspend fun
    fun getLobbies(): List<Lobby>
    fun getGamePlayUrl(): String
}

class LobbiesFakeServiceImpl : LobbiesService {
    override fun getLobbies(): List<Lobby> {
        return listOf(
            Lobby(0,"Poker Stars", "Renata", "jogo para aprender", 5,false, null,9,  emptyList<Player>()),
            Lobby(1,"Lucky Dice", "Diogo", "jogo para intermedios", 6,true, "passSecreta",4, emptyList<Player>()),
            Lobby(2,"Chelas Crew", "Humberto", "jogo para pros",7, false, null,1,  emptyList<Player>()),
            )
    }

    override fun getGamePlayUrl(): String{
        return "https://en.wikipedia.org/wiki/Poker_dice"
    }


}