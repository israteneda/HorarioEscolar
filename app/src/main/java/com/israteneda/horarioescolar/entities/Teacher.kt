package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Teacher")
data class Teacher(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val teacherId: Long,

    val name: String,

    @ColumnInfo(name = "cell_number")
    val cellNumber: String

)