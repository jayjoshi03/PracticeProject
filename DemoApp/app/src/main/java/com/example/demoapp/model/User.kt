package com.example.demoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user-table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String,
    var contact:String,
    var email:String
)
