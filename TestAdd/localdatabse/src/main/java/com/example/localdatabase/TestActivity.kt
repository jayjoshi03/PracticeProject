package com.example.localdatabase

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.localdatabase.databinding.ActivityTestBinding
import java.text.SimpleDateFormat
import java.util.Date


class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
//    private val userList = ArrayList<User>()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arr = arrayListOf(User(1, "Jay"), User(2, "Nigam"), User(3, "Joshi"))

        arr.forEach {
            it.id = 2
        }


        binding.btnDate.setOnClickListener {
            val timeMilli = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("MMM dd, yyy hh:mm a")
            dateFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
            val stringDate = dateFormat.format(Date(timeMilli))
            Toast.makeText(this, stringDate, Toast.LENGTH_SHORT).show()
        }

    }
}
