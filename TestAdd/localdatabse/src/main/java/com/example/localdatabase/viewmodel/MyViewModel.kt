package com.example.localdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel(var initialName:String) : ViewModel() {

    var nameObject = MutableLiveData<String>("My name is")

    val name: LiveData<String>
        get() = nameObject

    fun update() {
//        nameObject.value = "jay joshi" //Work is same of value and postValue but difference is value is work main thread but postValue work is Background Thread.
        nameObject.postValue(initialName)
    }
}