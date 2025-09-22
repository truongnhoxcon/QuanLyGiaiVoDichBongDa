package com.example.quanlygiaivodichbongda.database.dao

import androidx.room.*
import com.example.quanlygiaivodichbongda.database.entity.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Insert
    suspend fun insert(match: Match): Long

    @Query("SELECT * FROM matches ORDER BY thoiGian DESC")
    fun getAll(): Flow<List<Match>>
}
