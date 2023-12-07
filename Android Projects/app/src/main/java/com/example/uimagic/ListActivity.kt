package com.example.uimagic

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uimagic.databinding.AdapterListBinding

class ListActivity : AppCompatActivity() {
    lateinit var list : ArrayList<Model>
    lateinit var addList : ListAdapter
    private lateinit var binding : AdapterListBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdapterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            list = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras!!.getSerializable("LIST", Model::class.java)!! as ArrayList<Model>
            } else {
                intent.extras!!.getSerializable("LIST") as ArrayList<Model>
            }
        } catch(ex : Exception) {
            Log.e("Error", ex.toString())
        }

        addList = ListAdapter(list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = addList

        addList.setItem(list)


    }
}