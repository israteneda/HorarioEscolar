package com.israteneda.horarioescolar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.israteneda.horarioescolar.Util.Companion.openFragment
import com.israteneda.horarioescolar.fragments.*


class MainActivity: AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    private lateinit var db: AppDatabase

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Room Database

        db = AppDatabase.getAppDatabase(this)

//        var subjectId = db.subjectDao().insert(Subject(0,"Matemáticas", "#FFFFFF"))
//        db.timetableDao().insertAll(listOf(
//            Timetable(0, subjectId, Day.LUNES,"07:00", "09:00"),
//            Timetable(0, subjectId, Day.MARTES,"07:00", "09:00"),
//            Timetable(0, subjectId, Day.MIERCOLES,"07:00", "09:00")
//        ))

//        Log.i(TAG, db.subjectDao().getSubjectWithTimetables(6).toString())

        // UI Elements

        val bottomNav: BottomNavigationView  = findViewById(R.id.bottom_navigation)
        val fab: FloatingActionButton = findViewById(R.id.btn_floating);
        openFragment(this, HomeFragment.newInstance())
        fab.hide()

        // Bottom Navigation

        bottomNav.setOnNavigationItemSelectedListener {
            item: MenuItem ->
            when(item.itemId) {
                R.id.nav_home -> {
                    val fragment = HomeFragment.newInstance()
                    openFragment(this, fragment)
                    fab.hide()
                    true
                }
                R.id.nav_tasks -> {
                    val fragment = TaskFragment.newInstance()
                    openFragment(this, fragment)
                    fab.setImageResource(R.drawable.ic_note_add)
                    fab.show()
                    fab.setOnClickListener {
                        Toast.makeText(this, "Crear Tarea", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.nav_timetable -> {
                    val fragment = TimetableFragment.newInstance()
                    openFragment(this, fragment)
                    fab.setImageResource(R.drawable.ic_timetable_add)
                    fab.show()
                    fab.setOnClickListener {
                        val fragment = AddSubjectFragment.newInstance()
                        openFragment(this, fragment)
                        fab.hide()
                    }
                    true
                }
                R.id.nav_profile -> {
                    val fragment = ProfileFragment.newInstance()
                    openFragment(this, fragment)
                    fab.hide()
                    true
                }
                else -> false
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

}
