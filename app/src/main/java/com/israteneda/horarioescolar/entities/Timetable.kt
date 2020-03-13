package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timetable(
    @PrimaryKey val timetableId: Long,
    @ColumnInfo(name = "subject") val timetableSubjectId: Long,
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "start_time") val startTime: Double,
    @ColumnInfo(name = "end_time") val endTime: Double
)