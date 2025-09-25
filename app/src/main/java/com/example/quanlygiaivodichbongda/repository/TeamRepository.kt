package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.entity.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getAllTeams(): Flow<List<Team>>
    fun getTeamById(id: Long): Flow<Team?>
    suspend fun addTeam(team: Team): Long
    suspend fun updateTeam(team: Team)
    suspend fun deleteTeam(team: Team)
}
