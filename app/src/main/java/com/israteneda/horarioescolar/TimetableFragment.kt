package com.israteneda.horarioescolar

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.israteneda.horarioescolar.models.Timetable
import kotlinx.android.synthetic.main.fragment_timetable.*


class TimetableFragment : Fragment() {

    private val mTimetables = listOf(
        Timetable("Lunes", 12.0, 13.0),
        Timetable("Martes", 12.0, 13.0)
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
        fun newInstance() = TimetableFragment()
    }
}