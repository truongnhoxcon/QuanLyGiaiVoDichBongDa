package com.example.quanlygiaivodichbongda.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlygiaivodichbongda.database.entity.Player
import com.example.quanlygiaivodichbongda.repository.PlayerRepository
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val repository: PlayerRepository,
    private val teamId: Long
) : ViewModel() {

    val players = repository.getPlayersByTeam(teamId).asLiveData()

    fun addPlayer(player: Player) {
        viewModelScope.launch { repository.addPlayer(player) }
    }

    class Factory(
        private val repository: PlayerRepository,
        private val teamId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlayerViewModel(repository, teamId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${'$'}modelClass")
        }
    }
}


