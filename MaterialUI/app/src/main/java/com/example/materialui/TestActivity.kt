package com.example.materialui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TestActivity : AppCompatActivity() {
    private var counter = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val btn = findViewById<Button>(R.id.btn_show)
        val text = findViewById<TextView>(R.id.tv_count)

        btn.setOnClickListener {
            counter++
            text.text = counter.toString()
        }

    }
}