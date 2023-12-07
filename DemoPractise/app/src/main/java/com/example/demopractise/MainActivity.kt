package com.example.demopractise

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.demopractise.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShare.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
//            val msg = binding.editText.text.toString().trim()
//            val intent = Intent()
//            intent.action = Intent.ACTION_SEND
//            intent.putExtra(Intent.EXTRA_TEXT, msg)
//            intent.type = "text/plain"
//            startActivity(intent)
            //Multiple package User Thay tyre vaapar vanu
//            if(intent.resolveActivity(packageManager) != null) {
//                startActivity(intent)
//            }
        }

    }
}