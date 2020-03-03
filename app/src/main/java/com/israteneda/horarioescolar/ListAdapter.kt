package com.israteneda.horarioescolar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.israteneda.horarioescolar.models.Subject

class ListAdapter(private val list: List<Subject>)
    : RecyclerView.Adapter<SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SubjectViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val movie: Subject = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}