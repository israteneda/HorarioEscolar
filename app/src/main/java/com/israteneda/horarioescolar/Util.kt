package com.israteneda.horarioescolar

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Util {

    companion object {
        fun openFragment(activity: AppCompatActivity , fragment: Fragment) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}