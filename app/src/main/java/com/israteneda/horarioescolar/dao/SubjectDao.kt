package com.israteneda.horarioescolar.dao

import androidx.room.*
import com.israteneda.horarioescolar.entities.Subject
import com.israteneda.horarioescolar.entities.SubjectWithTimetables

@Dao
interface SubjectDao {

    @Insert
    fun insertSubject(subject: Subject)

    @Query("SELECT * FROM Subject")
    fun getAll(): List<Subject>

    @Delete
    fun deleteSubject(subject: Subject)

    @Transaction
    @Query("SELECT * FROM Subject")
    fun getSubjectWithTimetables(): List<SubjectWithTimetables>
}