package com.example.localdatabase.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.localdatabase.R


class CustomSpinnerActivity : AppCompatActivity() {


    private lateinit var checkbox: CheckBox
    private lateinit var spinner: Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkbox = findViewById(R.id.checkbox)
        spinner = findViewById(R.id.spinner)

        // Create an ArrayAdapter for the spinner
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        // Set an onCheckedChangeListener for the checkbox
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            spinner.isEnabled = isChecked
        }

    }

}
