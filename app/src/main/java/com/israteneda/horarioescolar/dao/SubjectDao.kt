package com.israteneda.horarioescolar.dao

import androidx.room.*
import com.israteneda.horarioescolar.entities.Subject
import com.israteneda.horarioescolar.entities.SubjectWithTimetables

@Dao
interface SubjectDao {

    @Insert
    fun insert(subject: Subject):Long

    @Update
    fun update(subject: Subject)

    @Delete
    fun delete(subject: Subject)

    @Query("SELECT * FROM Subject")
    fun getAll(): List<Subject>

    @Transaction
    @Query("SELECT * FROM Subject WHERE id = :subjectId")
    fun getSubjectWithTimetables(subjectId: Long): List<SubjectWithTimetables>
}