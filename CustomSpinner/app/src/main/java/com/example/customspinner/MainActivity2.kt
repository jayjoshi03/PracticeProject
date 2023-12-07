package com.example.customspinner

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    private var isChecked = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val check = findViewById<CheckBox>(R.id.checkbox_m)
        check.setOnClickListener {
            isChecked = !isChecked
            val message = if (isChecked) "Checked" else "Unchecked"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


    }
}