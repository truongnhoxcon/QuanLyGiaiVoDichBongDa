package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.entity.Match
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    fun getAllMatches(): Flow<List<Match>>
    suspend fun addMatch(match: Match): Long
}
