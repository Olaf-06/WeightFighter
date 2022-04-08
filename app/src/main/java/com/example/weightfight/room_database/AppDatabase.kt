package com.example.weightfight.room_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Data::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDataDao() : DataDao
}