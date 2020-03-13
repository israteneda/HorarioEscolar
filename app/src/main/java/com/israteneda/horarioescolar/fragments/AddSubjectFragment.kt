package com.israteneda.horarioescolar.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.israteneda.horarioescolar.R
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.fragment_add_subject.*

class AddSubjectFragment : Fragment() {

    companion object {
        private const val COLOR_SELECTED = "selectedColor"
        private const val NO_COLOR_OPTION = "noColorOption"
        fun newInstance() = AddSubjectFragment()
    }

    private var selectedColor: Int = ColorSheet.NO_COLOR
    private var noColorOption = false

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
        val selectedColorImageView = view?.findViewById(R.id.iv_selected_color) as ImageView
        val colors = resources.getIntArray(R.array.colors)

        leftArrow?.setOnClickListener {
            fragmentManager?.popBackStack()
            val fab: FloatingActionButton = activity!!.findViewById(R.id.btn_floating)
            fab.show()
        }

        // Color Sheet

        selectedColor = savedInstanceState?.getInt(COLOR_SELECTED) ?: colors.first()

        noColorOption = savedInstanceState?.getBoolean(NO_COLOR_OPTION) ?: false

        colorSheet.setOnClickListener {
            ColorSheet().cornerRadius(8)
                .colorPicker(
                    colors = colors,
                    noColorOption = noColorOption,
                    selectedColor = selectedColor,
                    listener = { color ->
                        selectedColor = color
                        ImageViewCompat.setImageTintList(
                            selectedColorImageView,
                            ColorStateList.valueOf(selectedColor)
                        )
                        Toast.makeText(context, selectedColor.toString(), Toast.LENGTH_LONG).show()
                    })
                .show(activity!!.supportFragmentManager)
        }

        // Radio Group

        rg_days.setOnCheckedChangeListener { group, checkedId ->
                val button: MaterialButton = view!!.findViewById(checkedId)
                Toast.makeText(context," On checked change :"+
                        " ${button.text}",
                    Toast.LENGTH_SHORT).show()
                println("click")
        }


    }
}