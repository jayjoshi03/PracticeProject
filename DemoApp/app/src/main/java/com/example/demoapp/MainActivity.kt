package com.example.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.demoapp.databinding.ActivityMainBinding
import com.example.demoapp.model.Demo
import com.example.demoapp.viewmodel.MyViewModel
import com.example.demoapp.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel =
            ViewModelProvider(this, MyViewModelFactory(applicationContext))[MyViewModel::class.java]

        demo(mainViewModel.getDemo())

        binding.btnNext.setOnClickListener {
            demo(mainViewModel.getNext())
        }

        binding.btnPrevious.setOnClickListener {
            demo(mainViewModel.getPrevious())
        }

    }

    private fun demo(demo: Demo) {
        binding.text = demo.text
        binding.author = demo.author
    }

}