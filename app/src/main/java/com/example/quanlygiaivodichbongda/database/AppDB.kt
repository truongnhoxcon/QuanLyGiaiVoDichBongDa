package com.example.quanlygiaivodichbongda.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quanlygiaivodichbongda.database.dao.*
import com.example.quanlygiaivodichbongda.database.entity.*

@Database(
    entities = [Team::class, Player::class, Match::class, Goal::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
    abstract fun matchDao(): MatchDao
    abstract fun goalDao(): GoalDao
}