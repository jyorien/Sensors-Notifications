package com.example.sensors_notifications.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TextEntity::class], version = 2)
abstract class RoomDb: RoomDatabase() {
    abstract fun roomDao(): RoomDao
    companion object {
        private var INSTANCE: RoomDb? = null

        fun getDatabase(context: Context): RoomDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    "room_db"
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE= instance
                instance
            }
        }
    }
}