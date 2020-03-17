package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Partial")
data class Partial(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val partialId: Long,

    @ColumnInfo(name = "subject_id")
    val subjectId: Long,

    @ColumnInfo(name = "grade")
    val grade: Double

)