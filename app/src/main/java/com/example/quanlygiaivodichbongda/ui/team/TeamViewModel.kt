package com.example.quanlygiaivodichbongda.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlygiaivodichbongda.database.entity.Team
import com.example.quanlygiaivodichbongda.repository.TeamRepository
import kotlinx.coroutines.launch

class TeamViewModel(
    private val repository: TeamRepository
) : ViewModel() {

    val teams = repository.getAllTeams().asLiveData()

    fun addTeam(team: Team) {
        viewModelScope.launch { repository.addTeam(team) }
    }

    fun updateTeam(team: Team) {
        viewModelScope.launch { repository.updateTeam(team) }
    }

    fun deleteTeam(team: Team) {
        viewModelScope.launch { repository.deleteTeam(team) }
    }

    class Factory(
        private val repository: TeamRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TeamViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${'$'}modelClass")
        }
    }
}


