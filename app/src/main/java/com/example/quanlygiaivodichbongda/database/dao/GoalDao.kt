package com.example.quanlygiaivodichbongda.database.dao

import androidx.room.*
import com.example.quanlygiaivodichbongda.database.entity.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert
    suspend fun insert(goal: Goal): Long

    @Query("SELECT * FROM goals WHERE matchId = :matchId")
    fun getByMatch(matchId: Long): Flow<List<Goal>>
}
