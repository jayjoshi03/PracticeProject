package com.example.localdatabase.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.localdatabase.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            Intent(this, MyIntentService::class.java).also {
                startService(it)
            }
        }

        binding.btn2.setOnClickListener {
            MyIntentService.isStopService()
        }
    }
}