package com.aguna.app.presentation.home.details.jemput

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.aguna.app.R
import com.aguna.app.model.TaskDto
import kotlinx.android.synthetic.main.fragment_jemput.*
import org.threeten.bp.OffsetDateTime

class JemputFragment : Fragment() {

//    private var taskDate: TaskDto = TaskDto()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jemput, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        txt_label_date.setOnClickListener {
//            showDatePickerDialog()
//        }

    }

//    private fun showDatePickerDialog() {
////        val newFragment: DialogFragment = DatePickerFragment.newInstance(this)
////        newFragment.show(context.getSupportFragmentManager(), "datePicker")
//
////        val newFragment: DialogFragment = DatePickerFragment.newInstance(this)
////        newFragment.show(requireFragmentManager(), "datePicker")
//
//    }

//    @SuppressLint("SetTextI18n")
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
//        taskDate = taskDate.copy(day = day, month = month + 1, year = year)
//        txt_label_date.text = "$day/${this.taskDate.month}/$year"
//    }
//
//    private fun setTaskDateTime(taskDto: TaskDto): OffsetDateTime {
//        return OffsetDateTime.of(
//            taskDto.year,
//            taskDto.month,
//            taskDto.day,
//            taskDto.hour,
//            taskDto.minute,
//            0,
//            0,
//            OffsetDateTime.now().offset
//        )
//    }

//    companion object {
//        /**
//         * Start [JemputFragment]
//         * @param context previous activity
//         */
//        fun start(context: Context): Intent {
//            return Intent(context, JemputFragment::class.java)
//        }
//    }


}