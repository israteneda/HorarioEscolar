package com.israteneda.horarioescolar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.israteneda.horarioescolar.models.Timetable

class ListAdapter(private val list: List<Timetable>)
    : RecyclerView.Adapter<SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SubjectViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val timetable: Timetable = list[position]
        holder.bind(timetable)
    }

    override fun getItemCount(): Int = list.size

}