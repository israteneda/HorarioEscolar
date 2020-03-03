package com.israteneda.horarioescolar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.israteneda.horarioescolar.models.Subject

class SubjectViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.subject_card_view_item, parent, false)) {

    private var mInitTimeView: TextView? = null
    private var mEndTimeView: TextView? = null
    private var mNameSubjectView: TextView? = null
    private var mTeacherView: TextView? = null

    init {
        mInitTimeView = itemView.findViewById(R.id.tv_init_time)
        mEndTimeView = itemView.findViewById(R.id.tv_end_time)
        mNameSubjectView = itemView.findViewById(R.id.tv_subject_name)
        mTeacherView = itemView.findViewById(R.id.tv_teacher)
    }

    fun bind(subject: Subject) {
        mInitTimeView?.text = subject.initTime
        mEndTimeView?.text = subject.endTime
        mNameSubjectView?.text = subject.name
        mTeacherView?.text = subject.teacher
    }

}