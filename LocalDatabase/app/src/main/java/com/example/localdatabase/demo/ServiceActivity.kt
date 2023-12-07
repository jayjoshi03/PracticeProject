package com.example.localdatabase.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.localdatabase.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {
    lateinit var binding: ActivityServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            Intent(this, MyService::class.java).also {
                startService(it)
            }
        }
        binding.btnStop.setOnClickListener {
            Intent(this, MyService::class.java).also {
                stopService(it)
                Log.d("SERVICE","Stop...")
            }
        }

        binding.btnData.setOnClickListener {
            Intent(this, MyService::class.java).also {
                var data = binding.etName.text.toString().trim()
                it.putExtra("EXTRA",data)
                startService(it)
            }
        }

    }
}