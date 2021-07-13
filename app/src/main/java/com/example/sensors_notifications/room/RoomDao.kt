package com.example.sensors_notifications.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {
    @Insert
    fun addText(text: TextEntity)

    @Query("SELECT * FROM TextEntity")
    fun getAllText(): LiveData<List<TextEntity>>
}