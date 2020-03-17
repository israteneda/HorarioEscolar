package com.israteneda.horarioescolar.entities

import androidx.room.*

@Entity(
    tableName = "Timetable",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Subject::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("subject_id"),
            onDelete = ForeignKey.CASCADE
        )
    )
)

data class Timetable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val timetableId: Long,

    @ColumnInfo(name = "subject_id")
    val subjectId: Long,

    @Embedded
    val day: Day,

    @ColumnInfo(name = "start_time")
    val startTime: String,

    @ColumnInfo(name = "end_time")
    val endTime: String

)