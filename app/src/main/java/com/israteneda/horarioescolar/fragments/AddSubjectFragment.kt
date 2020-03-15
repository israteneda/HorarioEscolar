package com.israteneda.horarioescolar.fragments

import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.view.View.OnTouchListener
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.israteneda.horarioescolar.R
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.fragment_add_subject.*
import java.text.SimpleDateFormat
import java.util.*


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

        val bottomNav: BottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)
        val leftArrow = view?.findViewById(R.id.leftArrow) as ImageView?
        val selectedColorImageView = view?.findViewById(R.id.iv_selected_color) as ImageView
        val colors = resources.getIntArray(R.array.colors)
        val startTime: TextView = view?.findViewById(R.id.et_start_time)
        val endTime: TextView = view?.findViewById(R.id.et_end_time)
        val addDayBtn: MaterialButton = view?.findViewById(R.id.btn_add_day)
        val mainContainer: LinearLayout = view?.findViewById(R.id.main_container)
        val dayContainer: LinearLayout = view?.findViewById(R.id.day_container)

        leftArrow?.setOnClickListener {
            fragmentManager?.popBackStack()
            val fab: FloatingActionButton = activity!!.findViewById(R.id.btn_floating)
            fab.show()
            bottomNav.visibility = View.VISIBLE
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
                        Toast.makeText(context, selectedColor.toString(), Toast.LENGTH_SHORT).show()
                    })
                .show(activity!!.supportFragmentManager)
        }

        iv_selected_color.setOnClickListener{
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
                        Toast.makeText(context, selectedColor.toString(), Toast.LENGTH_SHORT).show()
                    })
                .show(activity!!.supportFragmentManager)
        }

        // Radio Group

        rg_days.setOnCheckedChangeListener { group, checkedId ->
                val button: RadioButton = view!!.findViewById(checkedId)
                Toast.makeText(context," On checked change :"+
                        " ${button.text}",
                    Toast.LENGTH_SHORT).show()
                println("click")
        }

        // Time picker

        startTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                startTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        endTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                endTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        // Add Day

        addDayBtn.setOnClickListener {
            addDay()
        }


    }

    fun addDay(){

        val dp = context!!.resources.displayMetrics.density

        // Linear Layout Container of Day

        val layout_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        val layout_id = View.generateViewId()
        val layout_day = LinearLayout(context)
        layout_day.id = layout_id
        layout_day.orientation = LinearLayout.VERTICAL
        layout_day.layoutParams = layout_params

        // ImageView Close

        val iv_close = ImageView(context)
        val iv_close_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_close.isClickable = true
        iv_close_params.gravity = Gravity.RIGHT
        iv_close_params.setMargins(0, (8*dp).toInt(), (30*dp).toInt(), 0)
        iv_close.layoutParams = iv_close_params
        iv_close.setBackgroundResource(R.drawable.ic_close)

        iv_close.setOnClickListener {
            main_container.removeView(layout_day)
        }

        // Day TextView

        val tv_day = TextView(context)
        val tv_day_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_day_params.setMargins((30*dp).toInt(), 0, 0, 0)
        tv_day.layoutParams = tv_day_params
        tv_day.setTypeface(null, Typeface.BOLD)
        tv_day.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
        tv_day.setTextColor(Color.BLACK)
        tv_day.text = "Día"

        // RadioGroup and RadioButtons

        val days = arrayOf("L", "M", "Mi", "J", "V")

        val rg_days = RadioGroup(context)
        rg_days.orientation = RadioGroup.HORIZONTAL
        val rg_days_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        rg_days_params.setMargins((15*dp).toInt(), (15*dp).toInt() , 0, 0)
        rg_days.layoutParams = rg_days_params


        val rb_day_params = LinearLayout.LayoutParams((48*dp).toInt(), (48*dp).toInt())
        rb_day_params.setMargins((15*dp).toInt(), 0, 0, 0)

        val rb_day = arrayOfNulls<RadioButton>(5)
        for (i in 0..4) {
            rb_day[i] = RadioButton(context)
            rb_day[i]?.layoutParams = rb_day_params
            rb_day[i]?.textAlignment = View.TEXT_ALIGNMENT_CENTER
            rb_day[i]?.setTypeface(null, Typeface.BOLD)
            rb_day[i]?.buttonDrawable = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.custom_button_background)
            rb_day[i]?.setTextColor(ContextCompat.getColorStateList(activity!!.applicationContext, R.color.radio_text_selector))
            rb_day[i]?.setBackgroundResource(R.drawable.custom_button_background)
            rb_day[i]?.setText(days.get(i))
            rb_day.get(i)?.setId(i + 100)
            rg_days.addView(rb_day.get(i))
        }

        rg_days.setOnCheckedChangeListener { group, checkedId ->
            val button: RadioButton = view!!.findViewById(checkedId)
            Toast.makeText(context," On checked change :"+
                    " ${button.text}",
                Toast.LENGTH_SHORT).show()
            println("click")
        }

        // Start Time TextView

        val tv_start_time = TextView(context)
        val tv_start_time_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_start_time_params.setMargins((30*dp).toInt(), (15*dp).toInt() , 0, 0)
        tv_start_time.layoutParams = tv_start_time_params
        tv_start_time.setTypeface(null, Typeface.BOLD)
        tv_start_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
        tv_start_time.setTextColor(Color.BLACK)
        tv_start_time.text = "Hora de inidio"

        // Start Time TextView Time Picker

        val start_time_picker = TextView(context)
        val start_time_picker_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (50*dp).toInt())
        start_time_picker_params.setMargins((30*dp).toInt(), (15*dp).toInt() , (30*dp).toInt(), 0)
        start_time_picker.layoutParams = start_time_picker_params
        start_time_picker.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorLightGray))
        start_time_picker.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_start_time, 0, 0,0)
        start_time_picker.compoundDrawablePadding = (10*dp).toInt()
        start_time_picker.setPadding((10*dp).toInt())
        start_time_picker.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat())
        start_time_picker.hint = "Ingrese la hora inicial"

        start_time_picker.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                start_time_picker.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        // End Time TextView

        val tv_end_time = TextView(context)
        val tv_end_time_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_end_time_params.setMargins((30*dp).toInt(), (15*dp).toInt() , 0, 0)
        tv_end_time.layoutParams = tv_end_time_params
        tv_end_time.setTypeface(null, Typeface.BOLD)
        tv_end_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
        tv_end_time.setTextColor(Color.BLACK)
        tv_end_time.text = "Hora de inidio"

        // End Time TextView Time Picker

        val end_time_picker = TextView(context)
        val end_time_picker_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (50*dp).toInt())
        end_time_picker_params.setMargins((30*dp).toInt(), (15*dp).toInt() , (30*dp).toInt(), 0)
        end_time_picker.layoutParams = end_time_picker_params
        end_time_picker.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorLightGray))
        end_time_picker.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_end_time, 0, 0,0)
        end_time_picker.compoundDrawablePadding = (10*dp).toInt()
        end_time_picker.setPadding((10*dp).toInt())
        end_time_picker.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat())
        end_time_picker.hint = "Ingrese la hora final"

        end_time_picker.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                end_time_picker.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        // Divider

        val divider: View = View(context)
        val divider_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(1*dp).toInt())
        divider_params.setMargins(0, (25*dp).toInt() , 0, 0)
        divider.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, android.R.color.darker_gray))
        divider.layoutParams = divider_params

        // Add Views

        layout_day.addView(iv_close)
        layout_day.addView(tv_day)
        layout_day.addView(rg_days)
        layout_day.addView(tv_start_time)
        layout_day.addView(start_time_picker)
        layout_day.addView(tv_end_time)
        layout_day.addView(end_time_picker)
        layout_day.addView(divider)
        main_container.addView(layout_day, main_container.childCount - 1)

    }
}