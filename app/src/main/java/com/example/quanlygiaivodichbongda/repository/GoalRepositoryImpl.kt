package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.dao.GoalDao
import com.example.quanlygiaivodichbongda.database.entity.Goal
import kotlinx.coroutines.flow.Flow

class GoalRepositoryImpl(
    private val dao: GoalDao
) : GoalRepository {
    override fun getGoalsByMatch(matchId: Long): Flow<List<Goal>> = dao.getByMatch(matchId)
    override suspend fun addGoal(goal: Goal): Long = dao.insert(goal)
}
