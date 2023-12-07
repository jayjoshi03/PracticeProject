package com.example.localdatabase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.localdatabase.databinding.ActivityMainBinding
import com.example.localdatabase.viewmodel.MyViewModel
import com.example.localdatabase.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //WithOut ViewModelFactory
//        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        //With ViewModelFactory to and MyViewModelFactory(enter value)
        viewModel =
            ViewModelProvider(this, MyViewModelFactory("Jay"))[MyViewModel::class.java]

        //Value set Observer
//        viewModel.name.observe(this, Observer{
//            binding.name = it
//        })

        binding.text2.text = viewModel.name.value

//        //Without Observer using lifecycle method
        binding.lifecycleOwner = this


        //Set value button click
//        binding.btnSet.setOnClickListener {
//            viewModel.update()
//        }

        //XML onclick function
        binding.mainViewModel = viewModel

//        binding.btnResponse.setOnClickListener {

//            CoroutineScope(Dispatchers.Main).launch {
//                countResponse()
//            }

//            CoroutineScope(Dispatchers.Main).launch {
//                countResponse()
//            }


//            countResponse()

//        }

    }

//    private fun countResponse(){
//        for (i in 1..10000000L){
//        }
//    }

}