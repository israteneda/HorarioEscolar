package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Task")
data class Task(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val taskId: Long,

    @ColumnInfo(name = "subject_id")
    val subjectId: Long,

    val title: String,

    val description: String,

    val deadline: String

)