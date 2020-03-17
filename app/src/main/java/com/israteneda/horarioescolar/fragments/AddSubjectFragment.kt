package com.israteneda.horarioescolar.fragments

import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.israteneda.horarioescolar.R
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.fragment_add_subject.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class AddSubjectFragment : Fragment() {

    data class TextViewRadioGroup(
        val tv: TextView,
        val rg: RadioGroup
    )

    val days:HashMap<Int, String> = hashMapOf(
        0 to "LUNES",
        1 to "MARTES",
        2 to "MIÉRCOLES",
        3 to "JUEVES",
        4 to "VIERNES"
    )

    var addDayBtn: MaterialButton? = null;
    var listOfTextViewRadioGroup = arrayListOf<TextViewRadioGroup>()
    var listOfTextViews = arrayListOf<TextView>()

    companion object {
//        private const val COLOR_SELECTED = "selectedColor"
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
        val startTime: TextView = view?.findViewById(R.id.tv_start_time_picker)
        val endTime: TextView = view?.findViewById(R.id.tv_end_time_picker)
        addDayBtn = view?.findViewById(R.id.btn_add_day)

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

        addDayBtn?.setOnClickListener {
            if (listOfTextViewRadioGroup.size < 4){
                addDay()
            } else if (listOfTextViewRadioGroup.size == 4) {
                addDayBtn?.visibility = View.GONE
            }
        }

        // Save Subject

        save_container.setOnClickListener {

            if (et_name_subject.text.equals("") or et_name_subject.text.isEmpty()) {
                et_name_subject.setError("Ingrese el nombre de la materia")
            } else if (et_name_subject.text.count() < 3) {
                et_name_subject.setError("El nombre de la materia debe contener mínimo 3 letras")
            }

            if (selectedColor == ColorSheet.NO_COLOR){
                colorSheet.setError("")
            } else colorSheet.setError(null)

            var id: Int = rg_days.checkedRadioButtonId
            if ( id == -1) {
                tv_day.setError("")
            } else {
                var radio:RadioButton = view?.findViewById(id)
                var index = rg_days.indexOfChild(radio)
                Log.i(tag, index.toString())
                tv_day.setError(null)
            }


            if (startTime.text.equals("") or startTime.text.isEmpty()){
                startTime.setError("")
            } else startTime.setError(null)

            if (endTime.text.equals("") or endTime.text.isEmpty()){
                endTime.setError("")
            } else endTime.setError(null)


            listOfTextViews.forEach{ tv ->
                if (tv.text.equals("") or tv.text.isEmpty()){
                    tv.setError("")
                } else tv.setError(null)
                Log.i(tag, tv.text.toString())
            }

            listOfTextViewRadioGroup.forEach{ tvRadioGroup ->
                var rgId: Int = tvRadioGroup.rg.checkedRadioButtonId
                if (rgId == -1) {
                    tvRadioGroup.tv.setError("")
                } else {
                    var radio:RadioButton = view?.findViewById(rgId)
                    var index = tvRadioGroup.rg.indexOfChild(radio)
                    Log.i(tag, index.toString())
                    tvRadioGroup.tv.setError(null)
                }
            }

//            Snackbar.make(view, errorString, Snackbar.LENGTH_SHORT).show()
        }


    }

    fun addDay(){

        val dp = context!!.resources.displayMetrics.density

        // Linear Layout Container of Day

        val layout_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        val layout_day = LinearLayout(context)
        val layout_id = View.generateViewId()
        layout_day.id = layout_id
        layout_day.orientation = LinearLayout.VERTICAL
        layout_day.layoutParams = layout_params

        // ImageView Close

        val iv_close = ImageView(context)
        val iv_close_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_close.isClickable = true
        iv_close_params.gravity = Gravity.RIGHT
        iv_close_params.setMargins(0, (10*dp).toInt(), (30*dp).toInt(), 0)
        iv_close.layoutParams = iv_close_params
        iv_close.setBackgroundResource(R.drawable.ic_close)

        iv_close.setOnClickListener {
            main_container.removeView(layout_day)
            if (listOfTextViewRadioGroup.size == 4) {
                addDayBtn?.visibility = View.VISIBLE
            }
        }

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

        // RadioGroup and RadioButtons

        val days = arrayOf("L", "M", "Mi", "J", "V")

        val rg_days = RadioGroup(context)
        val rg_days_id = View.generateViewId()
        rg_days.id = rg_days_id
        rg_days.orientation = RadioGroup.HORIZONTAL
        val rg_days_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        rg_days_params.setMargins((15*dp).toInt(), (15*dp).toInt() , 0, 0)
        rg_days.layoutParams = rg_days_params

        val textViewRadioGroup = TextViewRadioGroup(tv_day, rg_days)
        listOfTextViewRadioGroup.add(textViewRadioGroup)

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

        val start_time_picker = TextView(context)
        val start_time_picker_id = View.generateViewId()
        start_time_picker.id = start_time_picker_id
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

        listOfTextViews.add(start_time_picker)

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
        val end_time_picker_id = View.generateViewId()
        end_time_picker.id = end_time_picker_id
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

        listOfTextViews.add(end_time_picker)

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