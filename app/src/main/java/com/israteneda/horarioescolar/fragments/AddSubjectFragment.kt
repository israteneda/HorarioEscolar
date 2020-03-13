package com.israteneda.horarioescolar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.israteneda.horarioescolar.R

class AddSubjectFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    // inflate your view here
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val leftArrow = view?.findViewById(R.id.leftArrow) as ImageView?
        leftArrow?.setOnClickListener {
            fragmentManager?.popBackStack()
            val fab: FloatingActionButton = activity!!.findViewById(R.id.btn_floating)
            fab.show()
        }
    }

    companion object {
        fun newInstance() =
            AddSubjectFragment()
    }
}