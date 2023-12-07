package com.example.demoapp.room

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demoapp.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("select * from `user-table`")
    fun getUserList() : MutableLiveData<List<User>>
}