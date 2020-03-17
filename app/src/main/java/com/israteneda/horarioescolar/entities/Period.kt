package com.israteneda.horarioescolar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Period")
data class Period(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val periodId: Long,

    @ColumnInfo(name = "period_name")
    val periodName: String,

    val institution: String,

    @ColumnInfo(name = "start_date")
    val startDate: String,

    @ColumnInfo(name = "end_date")
    val endDate: String

)