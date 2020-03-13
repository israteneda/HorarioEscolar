package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subject(
    @PrimaryKey val subjectId: Long,
    @ColumnInfo(name = "period") val subjectPeriodId: Long,
    @ColumnInfo(name = "teacher") val subjectTeacherId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "color") val color: String
)