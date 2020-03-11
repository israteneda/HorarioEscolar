package com.israteneda.horarioescolar

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav: BottomNavigationView  = findViewById(R.id.bottom_navigation)

        bottomNav.setOnNavigationItemSelectedListener {
            item: MenuItem ->
            when(item.itemId) {
                R.id.nav_home -> {
                    val fragment = HomeFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.nav_tasks -> {
                    val fragment = TaskFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.nav_timetable -> {
                    val fragment = TimetableFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.nav_profile -> {
                    val fragment = ProfileFragment.newInstance()
                    openFragment(fragment)
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
