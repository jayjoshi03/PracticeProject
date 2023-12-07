package com.example.materialui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.materialui.adapter.CarouselAdapter
import com.example.materialui.databinding.ActivityAppTopBarBinding
import com.google.android.material.carousel.CarouselLayoutManager

class CarouselActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppTopBarBinding
    private lateinit var carouselAdapter: CarouselAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppTopBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.carouselRecyclerView.clipChildren = false
        binding.carouselRecyclerView.clipToPadding = false

        // Example list of carousel images
        val carouselImages = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
        )

        carouselAdapter = CarouselAdapter(carouselImages)
        binding.carouselRecyclerView.adapter = carouselAdapter
        binding.carouselRecyclerView.layoutManager = CarouselLayoutManager()
    }
}
