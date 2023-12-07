package com.example.materialui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.materialui.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }
        binding.btnHome.setOnClickListener {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem->
            when(menuItem.itemId) {
                R.id.floating_bar -> {
                    Toast.makeText(this, "Floating", Toast.LENGTH_SHORT).show()
                    // Handle favorite icon press
                    true
                }

                R.id.home -> {
                    // Handle search icon press
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.person -> {
                    // Handle more item (inside overflow menu) press
                    Toast.makeText(this, "person", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
    }
}