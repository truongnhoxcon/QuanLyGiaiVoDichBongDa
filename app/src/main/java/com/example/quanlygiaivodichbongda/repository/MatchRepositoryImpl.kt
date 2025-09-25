package com.example.quanlygiaivodichbongda.repository

import com.example.quanlygiaivodichbongda.database.dao.MatchDao
import com.example.quanlygiaivodichbongda.database.entity.Match
import kotlinx.coroutines.flow.Flow

class MatchRepositoryImpl(
    private val dao: MatchDao
) : MatchRepository {
    override fun getAllMatches(): Flow<List<Match>> = dao.getAll()
    override suspend fun addMatch(match: Match): Long = dao.insert(match)
}
