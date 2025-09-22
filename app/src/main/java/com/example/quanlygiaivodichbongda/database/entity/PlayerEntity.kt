package com.example.quanlygiaivodichbongda.database.entity

import androidx.room.*

@Entity(
    tableName = "players",
    foreignKeys = [
        ForeignKey(
            entity = Team::class,
            parentColumns = ["id"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("teamId")]
)
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val teamId: Long,
    val ten: String,
    val soAo: Int?,
    val viTri: String, // GK, DF, MF, FW
    val ngaySinh: String? // ISO yyyy-MM-dd
)
