package com.example.demoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.demoapp.model.User
import com.example.demoapp.repository.RoomRepository

class RoomViewModel(var userRepository: RoomRepository) : ViewModel(){

    //Insert Function
   fun insert(user: User){

   }
}