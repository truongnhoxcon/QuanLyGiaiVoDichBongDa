package com.example.quanlygiaivodichbongda.database.entity

import androidx.room.*

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ten: String,
    val hlv: String?,
    val sanNha: String?
)