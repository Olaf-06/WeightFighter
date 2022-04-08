package com.example.weightfight.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {

    @Insert
    fun insert(data: Data)

    @Query("DELETE FROM data")
    fun deleteAll()

    @Query("SELECT * FROM data")
    fun getAllData() : MutableList<Data>
}