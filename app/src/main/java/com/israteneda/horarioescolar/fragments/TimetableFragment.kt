package com.israteneda.horarioescolar.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.israteneda.horarioescolar.AppDatabase
import com.israteneda.horarioescolar.adapters.ListAdapter
import com.israteneda.horarioescolar.R
import com.israteneda.horarioescolar.entities.Day
import com.israteneda.horarioescolar.entities.Timetable
import kotlinx.android.synthetic.main.fragment_timetable.*

// TODO: Revisar porque el fragment no respeta el Navigation Bar
// TODO: Desaparecer el Navigation Bar en crear materia
// TODO: Colocar los campos de la materia y ver como mostrar todos los días en el recycleview

class TimetableFragment : Fragment() {

    private lateinit var db: AppDatabase

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

            db = AppDatabase.getAppDatabase(context!!.applicationContext)

            val mTimetables = db.timetableDao().getTimetablesByDay(2)

                // set the custom adapter to the RecyclerView
            adapter = ListAdapter(mTimetables)
        }
    }

    companion object {
        fun newInstance() = TimetableFragment()
    }
}