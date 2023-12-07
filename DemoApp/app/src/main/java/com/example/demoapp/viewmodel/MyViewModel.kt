package com.example.demoapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.demoapp.model.Demo
import com.google.gson.Gson

class MyViewModel(private val context: Context) : ViewModel() {
    private var demoList: Array<Demo> = emptyArray()
    private var index = 0

    init {
        demoList = loadDemoList()
    }

    private fun loadDemoList(): Array<Demo> {
        val inputFile = context.assets.open("demo.json")
        val size: Int = inputFile.available()
        val buffer = ByteArray(size)
        inputFile.read(buffer)
        inputFile.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Demo>::class.java)
    }

    fun getDemo() = demoList[index]

    fun getNext() = demoList[++index]
    fun getPrevious() = demoList[--index]


}