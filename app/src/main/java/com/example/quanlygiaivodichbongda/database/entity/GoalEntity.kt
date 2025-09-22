package com.example.quanlygiaivodichbongda.database.entity

import androidx.room.*

@Entity(
    tableName = "goals",
    foreignKeys = [
        ForeignKey(entity = Match::class, parentColumns = ["id"], childColumns = ["matchId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Team::class, parentColumns = ["id"], childColumns = ["teamId"]),
        ForeignKey(entity = Player::class, parentColumns = ["id"], childColumns = ["playerId"])
    ],
    indices = [Index("matchId"), Index("teamId"), Index("playerId")]
)
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val matchId: Long,
    val teamId: Long,
    val playerId: Long?,
    val phut: Int,
    val loai: String // NORMAL, PEN, OG
)
