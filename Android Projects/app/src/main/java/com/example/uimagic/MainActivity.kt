package com.example.uimagic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.uimagic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding : ActivityMainBinding
    var list = ArrayList<Model>()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v : View?) {
        when(v!!.id) {
            R.id.addBtn -> {
                newAddView()
            }

            R.id.btnSubmit -> {
                if(checkValidRead()) {
                    val intent = Intent(this, ListActivity::class.java)
                    intent.putExtra("LIST", list)
                    startActivity(intent)
                }
            }
        }
    }

    private fun checkValidRead() : Boolean {
        list.clear()
        var bool : Boolean = true

        for(i in 0 until binding.listLayout.childCount) {
            val view = binding.listLayout.getChildAt(i)
            val editText = view.findViewById<EditText>(R.id.editText)
            val listAdd = Model("")
            if(editText.text.isNotEmpty()) {
                listAdd.name = editText.text.toString().trim()
            } else {
                bool = false
            }

            list.add(listAdd)
        }

        return bool
    }

    private fun newAddView() {
        val view = layoutInflater.inflate(R.layout.add_layout, null, false)

        val editText = view.findViewById<EditText>(R.id.editText)
        val closeBtn = view.findViewById<Button>(R.id.closeBtn)

        closeBtn.setOnClickListener {
            removeView(view)
        }

        binding.listLayout.addView(view)
    }

    private fun removeView(v : View?) {
        binding.listLayout.removeView(v)
    }
}