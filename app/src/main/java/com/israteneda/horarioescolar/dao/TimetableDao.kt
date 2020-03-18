package com.israteneda.horarioescolar.dao

import androidx.room.*
import com.israteneda.horarioescolar.entities.Day
import com.israteneda.horarioescolar.entities.Timetable

@Dao
interface TimetableDao {

    @Query("SELECT * FROM Timetable WHERE idx = :idxDay")
    fun getTimetablesByDay(idxDay: Int): List<Timetable>

    @Insert
    fun insert(timetable: Timetable)

    @Insert
    fun insertAll(timetables: List<Timetable>)

    @Update
    fun update(timetable: Timetable)

    @Delete
    fun delete(timetable: Timetable)

}