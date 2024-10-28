package com.dicoding.event.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EventEntity::class], version = 1)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var instance: EventDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): EventDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event.db"
                ).build()
            }
    }
}