package com.israteneda.horarioescolar.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SubjectWithTimetables(

    @Embedded
    var subject: Subject,

    @Relation(parentColumn = "id", entityColumn = "subject_id", entity = Timetable::class)
    var timetables: List<Timetable>

)