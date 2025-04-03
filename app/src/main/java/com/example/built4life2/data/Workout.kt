package com.example.built4life2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "workout_details")
    val workoutDetails: String,
    val reps: String,
    val distance: String,
    val weight: String,
    val info: String,
    val notes: String
)