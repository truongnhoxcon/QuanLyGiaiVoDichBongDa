package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.dao.PlayerDao
import com.example.quanlygiaivodichbongda.database.entity.Player
import kotlinx.coroutines.flow.Flow

class PlayerRepositoryImpl(
    private val dao: PlayerDao
) : PlayerRepository {
    override fun getPlayersByTeam(teamId: Long): Flow<List<Player>> = dao.getByTeam(teamId)
    override suspend fun addPlayer(player: Player): Long = dao.insert(player)
}
