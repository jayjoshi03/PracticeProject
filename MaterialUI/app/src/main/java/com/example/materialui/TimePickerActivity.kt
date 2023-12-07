package com.example.materialui

import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.materialui.databinding.ActivityTimePickerBinding
import java.util.Calendar
import java.util.Locale

class TimePickerActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityTimePickerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTimepicker.setOnClickListener {
            showTimePickerDialog()
        }

    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create and show the time picker dialog
        val timePickerDialog = TimePickerDialog(this, this, hour, minute, false)
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        // Format the selected time and display it in the edit text
        val selectedTime = Calendar.getInstance()
        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        selectedTime.set(Calendar.MINUTE, minute)

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedTime = timeFormat.format(selectedTime.time)

        binding.timeEditText.setText(formattedTime)
    }
}