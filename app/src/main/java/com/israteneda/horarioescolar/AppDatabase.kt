package com.israteneda.horarioescolar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.israteneda.horarioescolar.dao.SubjectDao
import com.israteneda.horarioescolar.entities.Subject
import com.israteneda.horarioescolar.entities.Timetable


@Database(
    entities = arrayOf(
        Subject::class,
        Timetable::class
    ),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

    companion object {

        // For Singleton instantiation
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "HorarioEscolarDB"
                ).allowMainThreadQueries().build()

            }

            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}