package com.example.quanlygiaivodichbongda.database.dao

import androidx.room.*
import com.example.quanlygiaivodichbongda.database.entity.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert
    suspend fun insert(team: Team): Long

    @Update
    suspend fun update(team: Team)

    @Delete
    suspend fun delete(team: Team)

    @Query("SELECT * FROM teams ORDER BY ten")
    fun getAll(): Flow<List<Team>>

    @Query("SELECT * FROM teams WHERE id = :id")
    fun getById(id: Long): Flow<Team?>
}
