package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    @PrimaryKey val taskId: Long,
    @ColumnInfo(name = "subject") val taskSubjectId: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "deadline") val deadline: Date
)