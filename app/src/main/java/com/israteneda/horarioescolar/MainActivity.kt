package com.israteneda.horarioescolar

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.israteneda.horarioescolar.fragments.*


class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav: BottomNavigationView  = findViewById(R.id.bottom_navigation)
        val fab: FloatingActionButton = findViewById(R.id.btn_floating);
        openFragment(HomeFragment.newInstance())
        fab.hide()

        bottomNav.setOnNavigationItemSelectedListener {
            item: MenuItem ->
            when(item.itemId) {
                R.id.nav_home -> {
                    val fragment = HomeFragment.newInstance()
                    openFragment(fragment)
                    fab.hide()
                    true
                }
                R.id.nav_tasks -> {
                    val fragment = TaskFragment.newInstance()
                    openFragment(fragment)
                    fab.setImageResource(R.drawable.ic_note_add)
                    fab.show()
                    fab.setOnClickListener {
                        Toast.makeText(this, "Crear Tarea", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                R.id.nav_timetable -> {
                    val fragment = TimetableFragment.newInstance()
                    openFragment(fragment)
                    fab.setImageResource(R.drawable.ic_timetable_add)
                    fab.show()
                    fab.setOnClickListener {
                        val fragment = AddSubjectFragment.newInstance()
                        openFragment(fragment)
                        fab.hide()
                    }
                    true
                }
                R.id.nav_news -> {
                    val fragment = NewsFragment.newInstance()
                    openFragment(fragment)
                    fab.hide()
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
