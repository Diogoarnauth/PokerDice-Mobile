package com.example.chelasmultiplayerpokerdice.lobbies

import com.example.chelasmultiplayerpokerdice.domain.LobbyInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LobbiesRepository(private val service: LobbiesService) {

    val lobbies: Flow<List<LobbyInfo>> = flow {
        while (true) {
            try {
                val data = service.fetchLobbiesList()
                emit(data)
            } catch (e: Exception) {
                // TODO("LIDAR COM OS ERROS"
            }
            delay(2000)
        }
    }
}