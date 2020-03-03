package com.israteneda.horarioescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : SingleFragmentActivity() {
    override fun createFragment() = MainFragment.newInstance()
}
