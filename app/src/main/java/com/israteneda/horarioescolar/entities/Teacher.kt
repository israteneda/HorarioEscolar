package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teacher(
    @PrimaryKey val teacherId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cell_number") val cellNumber: String
)