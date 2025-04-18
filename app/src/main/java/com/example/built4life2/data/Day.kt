package com.example.built4life2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class Day(
    @PrimaryKey(autoGenerate = true)
    val dayId: Int = 0,
    val title: String,
    val programId: Int,
)