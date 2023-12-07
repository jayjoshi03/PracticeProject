package com.example.materialui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.materialui.databinding.ActivityDateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatePickerActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    lateinit var binding: ActivityDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener to open the date picker dialog
        binding.datepickerButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        // Get the current date as the default selection in the dialog
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create and show the date picker dialog
        val datePickerDialog = DatePickerDialog(this, this, year, month, day)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Format the selected date and display it in the edit text
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)

        binding.dateEditText.setText(formattedDate)
    }
}

