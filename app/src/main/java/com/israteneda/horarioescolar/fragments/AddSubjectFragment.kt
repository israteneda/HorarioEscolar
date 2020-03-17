package com.israteneda.horarioescolar.fragments

import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.setPadding
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.israteneda.horarioescolar.AppDatabase
import com.israteneda.horarioescolar.R
import com.israteneda.horarioescolar.entities.Day
import com.israteneda.horarioescolar.entities.Subject
import com.israteneda.horarioescolar.entities.Timetable
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.fragment_add_subject.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


class AddSubjectFragment : Fragment() {

    companion object {
        //        private const val COLOR_SELECTED = "selectedColor"
        private const val NO_COLOR_OPTION = "noColorOption"
        fun newInstance() = AddSubjectFragment()
    }

    data class TimetableUI(
        val tv_day: TextView,
        val rg_days: RadioGroup,
        val tv_start_time: TextView,
        val tv_end_time: TextView
    )

    private lateinit var db: AppDatabase
    var addTimetableBtn: MaterialButton? = null;
    var listOfTimetableUI = arrayListOf<TimetableUI>()

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

        // Database

        db = AppDatabase.getAppDatabase(context!!.applicationContext)

        // UI Elements

        val bottomNav: BottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)
        val leftArrow = view?.findViewById(R.id.leftArrow) as ImageView?
        val selectedColorImageView = view?.findViewById(R.id.iv_selected_color) as ImageView
        val colors = resources.getIntArray(R.array.colors)
        val startTime: TextView = view?.findViewById(R.id.tv_start_time_picker)
        val endTime: TextView = view?.findViewById(R.id.tv_end_time_picker)
        addTimetableBtn = view?.findViewById(R.id.btn_add_timetable)

        leftArrow?.setOnClickListener {
            fragmentManager?.popBackStack()
            val fab: FloatingActionButton = activity!!.findViewById(R.id.btn_floating)
            fab.show()
            bottomNav.visibility = View.VISIBLE
        }

        // Color Sheet
        // selectedColor = savedInstanceState?.getInt(COLOR_SELECTED) ?: colors.first()
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

        // ImageView Color

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

        // Day Radio Group

        rg_days.setOnCheckedChangeListener { group, checkedId ->
                val button: RadioButton = view!!.findViewById(checkedId)
                Toast.makeText(context," On checked change :"+
                        " ${button.text}",
                    Toast.LENGTH_SHORT).show()
                println("click")
        }

        // Time pickers

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

        addTimetableBtn?.setOnClickListener {
            if (listOfTimetableUI.size < 4){
                addTimetable()
            } else if (listOfTimetableUI.size == 4) {
                addTimetableBtn?.visibility = View.GONE
            }
        }

        listOfTimetableUI.add(TimetableUI(tv_day, rg_days, startTime, endTime))

        // Save Subject

        save_container.setOnClickListener {

            // Subject validation

            var validatedSubject = false
            var validatedColor = false

            if (et_name_subject.text.equals("") or et_name_subject.text.isEmpty()) {
                et_name_subject.setError("Ingrese el nombre de la materia")
            } else if (et_name_subject.text.count() < 3) {
                et_name_subject.setError("El nombre de la materia debe contener mínimo 3 letras")
            } else validatedSubject = true

            if (selectedColor == ColorSheet.NO_COLOR){
                colorSheet.setError("")
            } else {
                colorSheet.setError(null)
                validatedColor = true
            }

            // List of timetables validation
            var listOfIndex = arrayListOf<Int>()
            var listOfValidatedDay = arrayListOf<Boolean>()
            var listOfValidatedStartTime = arrayListOf<Boolean>()
            var listOfValidatedEndTime = arrayListOf<Boolean>()

            listOfTimetableUI.forEach{ timetableUI ->

                var validatedDay = false
                var validatedStartTime = false
                var validatedEndTime = false

                var rgId: Int = timetableUI.rg_days.checkedRadioButtonId
                if (rgId == -1) {
                    timetableUI.tv_day.setError("")
                } else {
                    var radioBtn:RadioButton = view?.findViewById(rgId)
                    var index = timetableUI.rg_days.indexOfChild(radioBtn)
                    listOfIndex.add(index)
                    Log.i(tag, index.toString())
                    validatedDay = true
                    timetableUI.tv_day.setError(null)
                }
                listOfValidatedDay.add(validatedDay)

                if (timetableUI.tv_start_time.text.equals("") or timetableUI.tv_start_time.text.isEmpty()){
                    timetableUI.tv_start_time.setError("")
                } else {
                    validatedStartTime = true
                    timetableUI.tv_start_time.setError(null)
                }
                listOfValidatedStartTime.add(validatedStartTime)

                if (timetableUI.tv_end_time.text.equals("") or timetableUI.tv_end_time.text.isEmpty()){
                    timetableUI.tv_end_time.setError("")
                } else {
                    validatedEndTime = true
                    timetableUI.tv_end_time.setError(null)
                }
                listOfValidatedEndTime.add(validatedEndTime)


                Log.i(tag, timetableUI.tv_start_time.text.toString())
            }

            // Save on database

            if (validatedColor and validatedSubject) {
                if (!listOfValidatedDay.contains(false) and !listOfValidatedStartTime.contains(false) and !listOfValidatedEndTime.contains(false)) {
                    var subjectId = db.subjectDao().insert(Subject(0, et_name_subject.text.toString(), selectedColor))
                    for (i in 0..(listOfTimetableUI.size - 1)) {
                        db.timetableDao().insert(Timetable(
                            0,
                            subjectId,
                            Day.days.get(listOfIndex.get(i)),
                            listOfTimetableUI.get(i).tv_start_time.text.toString(),
                            listOfTimetableUI.get(i).tv_end_time.text.toString()
                        ))
                    }
                    Toast.makeText(context, "Materia Creada", Toast.LENGTH_LONG).show()
                    Log.i(tag, db.subjectDao().getSubjectWithTimetables(subjectId).toString())
                }
            }

//            Snackbar.make(view, errorString, Snackbar.LENGTH_SHORT).show()
        }


    }

    fun addTimetable(){

        val dp = context!!.resources.displayMetrics.density

        // Linear Layout Container of Timetable

        val layout_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        val layout_timetable = LinearLayout(context)
        val layout_id = View.generateViewId()
        layout_timetable.id = layout_id
        layout_timetable.orientation = LinearLayout.VERTICAL
        layout_timetable.layoutParams = layout_params

        // Day TextView

        val tv_day = TextView(context)
        val tv_day_id = View.generateViewId()
        tv_day.id = tv_day_id
        val tv_day_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_day_params.setMargins((30*dp).toInt(), 0, 0, 0)
        tv_day.layoutParams = tv_day_params
        tv_day.compoundDrawablePadding = (10*dp).toInt()
        tv_day.setTypeface(null, Typeface.BOLD)
        tv_day.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
        tv_day.setTextColor(Color.BLACK)
        tv_day.text = "Día"

        // Days RadioGroup and RadioButtons

        val days = arrayOf("L", "M", "Mi", "J", "V")

        val rg_days = RadioGroup(context)
        val rg_days_id = View.generateViewId()
        rg_days.id = rg_days_id
        rg_days.orientation = RadioGroup.HORIZONTAL
        val rg_days_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        rg_days_params.setMargins((15*dp).toInt(), (15*dp).toInt() , 0, 0)
        rg_days.layoutParams = rg_days_params

        val rb_day_params = LinearLayout.LayoutParams((48*dp).toInt(), (48*dp).toInt())
        rb_day_params.setMargins((15*dp).toInt(), 0, 0, 0)

        val rb_day = arrayOfNulls<RadioButton>(5)
        for (i in 0..4) {
            rb_day[i] = RadioButton(context)
            val rb_day_id = View.generateViewId()
            rb_day[i]?.id = rb_day_id
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

        val picker_start_time = TextView(context)
        val picker_start_time_id = View.generateViewId()
        picker_start_time.id = picker_start_time_id
        val picker_start_time_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (50*dp).toInt())
        picker_start_time_params.setMargins((30*dp).toInt(), (15*dp).toInt() , (30*dp).toInt(), 0)
        picker_start_time.layoutParams = picker_start_time_params
        picker_start_time.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorLightGray))
        picker_start_time.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_start_time, 0, 0,0)
        picker_start_time.compoundDrawablePadding = (10*dp).toInt()
        picker_start_time.setPadding((10*dp).toInt())
        picker_start_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat())
        picker_start_time.hint = "Ingrese la hora inicial"

        picker_start_time.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                picker_start_time.setText(SimpleDateFormat("HH:mm").format(cal.time))
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

        val picker_end_time = TextView(context)
        val picker_end_time_id = View.generateViewId()
        picker_end_time.id = picker_end_time_id
        val picker_end_time_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (50*dp).toInt())
        picker_end_time_params.setMargins((30*dp).toInt(), (15*dp).toInt() , (30*dp).toInt(), 0)
        picker_end_time.layoutParams = picker_end_time_params
        picker_end_time.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorLightGray))
        picker_end_time.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_end_time, 0, 0,0)
        picker_end_time.compoundDrawablePadding = (10*dp).toInt()
        picker_end_time.setPadding((10*dp).toInt())
        picker_end_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat())
        picker_end_time.hint = "Ingrese la hora final"

        picker_end_time.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                picker_end_time.setText(SimpleDateFormat("HH:mm").format(cal.time))
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

        // ImageView Close

        val iv_close = ImageView(context)
        val iv_close_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_close.isClickable = true
        iv_close_params.gravity = Gravity.RIGHT
        iv_close_params.setMargins(0, (10*dp).toInt(), (30*dp).toInt(), 0)
        iv_close.layoutParams = iv_close_params
        iv_close.setBackgroundResource(R.drawable.ic_close)

        // Create TimetableUI

        val timetablUI = TimetableUI(tv_day, rg_days, picker_start_time, picker_end_time)
        listOfTimetableUI.add(timetablUI)

        // ImageView Close Click

        iv_close.setOnClickListener {
            main_container.removeView(layout_timetable)
            if (listOfTimetableUI.size == 4) {
                addTimetableBtn?.visibility = View.VISIBLE
            }
            listOfTimetableUI.remove(timetablUI)
        }

        // Add Views

        layout_timetable.addView(iv_close)
        layout_timetable.addView(tv_day)
        layout_timetable.addView(rg_days)
        layout_timetable.addView(tv_start_time)
        layout_timetable.addView(picker_start_time)
        layout_timetable.addView(tv_end_time)
        layout_timetable.addView(picker_end_time)
        layout_timetable.addView(divider)
        main_container.addView(layout_timetable, main_container.childCount - 1)

    }
}