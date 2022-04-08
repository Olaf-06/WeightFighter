package com.example.weightfight.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Data(@PrimaryKey val id: Int, val weight: Float, val distance: Float,
                val year: Int, val month: Int, val day: Int)
