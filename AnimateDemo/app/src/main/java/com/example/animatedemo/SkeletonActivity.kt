package com.example.animatedemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.animatedemo.databinding.ActivitySkeletonBinding

class SkeletonActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySkeletonBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkeletonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
//            binding.shimmerLayout.stopShimmer()
//            binding.shimmerLayout.visibility = View.GONE
        }, 2000)
    }

    // Start the shimmer animation when the activity becomes visible
//    override fun onResume() {
//        super.onResume()
//        binding.shimmerLayout.startShimmer()
//    }
//
    // Stop the shimmer animation when the activity is paused or stopped
//    override fun onPause() {
//        binding.shimmerLayout.stopShimmer()
//        super.onPause()
//    }
}

