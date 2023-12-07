package com.example.animatedemo

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.animatedemo.abstractFunction.DoubleClickListener
import com.example.animatedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowInsetsController=WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior=WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets->
            if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                ||windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
            ) {
                binding.btnCheck.setOnClickListener {
                    // Hide both the status bar and the navigation bar.
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                }
            } else {
                binding.btnCheck.setOnClickListener {
                    // Show both the status bar and the navigation bar.
                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                }
            }
            view.onApplyWindowInsets(windowInsets)
        }
        binding.btnCheck.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v : View?) {
                Toast.makeText(this@MainActivity, "Double Click is Success!!", Toast.LENGTH_SHORT).show()
            }
        })
    }

}