package com.example.submissionfundamental1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submissionfundamental1.data.model.UserResponse

@Database(entities = [UserResponse.Item::class], version = 1, exportSchema = false)
abstract class DatabaseApp : RoomDatabase(){
    abstract fun userDao() : UserDao
}