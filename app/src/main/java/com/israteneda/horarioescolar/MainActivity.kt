package com.israteneda.horarioescolar

class MainActivity : SingleFragmentActivity() {
    override fun createFragment() = MainFragment.newInstance()
}
