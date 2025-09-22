package com.example.quanlygiaivodichbongda.database.entity

import androidx.room.*

@Entity(
    tableName = "matches",
    foreignKeys = [
        ForeignKey(entity = Team::class, parentColumns = ["id"], childColumns = ["doiNhaId"]),
        ForeignKey(entity = Team::class, parentColumns = ["id"], childColumns = ["doiKhachId"])
    ],
    indices = [Index("doiNhaId"), Index("doiKhachId")]
)
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val doiNhaId: Long,
    val doiKhachId: Long,
    val san: String?,
    val thoiGian: Long, // epoch millis
    val trongTai: String?,
    val khanGia: Int?
)
