package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.entity.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun getGoalsByMatch(matchId: Long): Flow<List<Goal>>
    suspend fun addGoal(goal: Goal): Long
}
