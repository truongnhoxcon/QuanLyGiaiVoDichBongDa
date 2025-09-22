package com.example.quanlygiaivodichbongda.database.dao

import androidx.room.*
import com.example.quanlygiaivodichbongda.database.entity.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert
    suspend fun insert(player: Player): Long

    @Query("SELECT * FROM players WHERE teamId = :teamId ORDER BY viTri, ten")
    fun getByTeam(teamId: Long): Flow<List<Player>>
}
