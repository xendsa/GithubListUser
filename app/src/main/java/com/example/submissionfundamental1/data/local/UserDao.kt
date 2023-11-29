package com.example.submissionfundamental1.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submissionfundamental1.data.model.UserResponse

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserResponse.Item)

    @Query("SELECT * FROM User")
    fun loadAll() : LiveData<MutableList<UserResponse.Item>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): UserResponse.Item

    @Query("SELECT COUNT(*) FROM User WHERE id = :id")
    fun userOnList(id: Int): Int

    @Delete
    fun delete(user: UserResponse.Item)
}