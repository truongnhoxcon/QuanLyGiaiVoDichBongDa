package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.dao.TeamDao
import com.example.quanlygiaivodichbongda.database.entity.Team
import kotlinx.coroutines.flow.Flow

class TeamRepositoryImpl(
    private val dao: TeamDao
) : TeamRepository {
    override fun getAllTeams(): Flow<List<Team>> = dao.getAll()
    override fun getTeamById(id: Long): Flow<Team?> = dao.getById(id)
    override suspend fun addTeam(team: Team): Long = dao.insert(team)
    override suspend fun updateTeam(team: Team) = dao.update(team)
    override suspend fun deleteTeam(team: Team) = dao.delete(team)
}
