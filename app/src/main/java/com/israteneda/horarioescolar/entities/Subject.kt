package com.israteneda.horarioescolar.entities

import androidx.room.*

@Entity(
    tableName = "Subject"
//    foreignKeys = arrayOf(
//        ForeignKey(
//            entity = Period::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("period_id")
//        ),
//        ForeignKey(
//            entity = Teacher::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("teacher_id")
//        )
//    )
)
data class Subject(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val subjectId: Long,

//    @ColumnInfo(name = "period_id")
//    val periodId: Long,

//    @ColumnInfo(name = "teacher_id")
//    val teacherId: Long,

    val name: String,

    val color: String
)



