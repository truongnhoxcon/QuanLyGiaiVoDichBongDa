package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.entity.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getPlayersByTeam(teamId: Long): Flow<List<Player>>
    suspend fun addPlayer(player: Player): Long
    // Sau này có thể thêm update/delete nếu cần
}
