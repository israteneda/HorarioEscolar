package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Partial(
    @PrimaryKey val partialId: Long,
    @ColumnInfo(name = "subject") val partialSubjectId: Long,
    @ColumnInfo(name = "grade") val grade: Double
)