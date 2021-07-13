package com.example.sensors_notifications.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TextEntity (
    @PrimaryKey (autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo
    val text: String
        )