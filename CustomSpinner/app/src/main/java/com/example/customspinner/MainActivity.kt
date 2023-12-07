package com.example.customspinner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private var isChecked = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.spinner)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val spinnerAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_item,
            viewModel.spinnerItems
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(applicationContext).inflate(
                    R.layout.custom_spinner_item,
                    parent,
                    false
                )

                val checkboxItem: CheckBox = view.findViewById(R.id.checkbox)
                val itemText: TextView = view.findViewById(R.id.tv_text)

                val item = getItem(position)
                checkboxItem.isChecked = itemSelected(position)
                itemText.text = item

                checkboxItem.setOnClickListener {
                    selectedItems[position] = checkboxItem.isChecked
                    updateSelectedItemsToast()
                }

                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup,
            ): View {
                return getView(position, convertView, parent)
            }
        }

        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                // Implement
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val button = findViewById<Button>(R.id.btn_show)
        button.setOnClickListener {
            val selectedItemsList = mutableListOf<String>()
            for (i in selectedItems.indices) {
                if (selectedItems[i]) {
                    val item = spinnerAdapter.getItem(i)
                    item?.let { selectedItemsList.add(it) }
                }
            }
            val selectedItems = selectedItemsList.joinToString(", ")
            Toast.makeText(applicationContext, "Selected items: $selectedItems", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun updateSelectedItemsToast() {
        val selectedItemsList = mutableListOf<String>()
        for (i in selectedItems.indices) {
            if (selectedItems[i]) {
                val item = viewModel.spinnerItems[i]
                selectedItemsList.add(item)
            }
        }
        val selectedItems = selectedItemsList.joinToString(", ")
        Toast.makeText(applicationContext, "Selected items: $selectedItems", Toast.LENGTH_SHORT)
            .show()
    }

    private val selectedItems = BooleanArray(5)

    private fun itemSelected(position: Int): Boolean {
        return selectedItems[position]
    }

}

