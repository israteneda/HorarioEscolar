package com.israteneda.horarioescolar.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.israteneda.horarioescolar.ListAdapter
import com.israteneda.horarioescolar.R
import com.israteneda.horarioescolar.entities.Day
import com.israteneda.horarioescolar.entities.Timetable
import kotlinx.android.synthetic.main.fragment_timetable.*


class TimetableFragment : Fragment() {

    private val mTimetables = listOf(
        Timetable(1,1, Day(0,"Lunes"), "07:00", "09:00"),
        Timetable(2, 1, Day(2,"Miercoles"), "07:00", "09:00")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    // inflate your view here
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_timetable, container, false)


    // Wait until your View is guaranteed not null to grab View elements
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_subjects.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = ListAdapter(mTimetables)
        }
    }

    companion object {
        fun newInstance() =
            TimetableFragment()
    }
}