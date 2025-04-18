package com.example.built4life2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Int = 0,
    val title: String,
    val description: String,
    val firstSetReps: String,
    val totalReps: String,
    val weight: String,
    val beginner: String,
    val novice: String,
    val intermediate: String,
    val advanced: String,
    val elite: String,
    val favorite: Boolean = false,
    val monday: Boolean = false,
    val tuesday: Boolean = false,
    val wednesday: Boolean = false,
    val thursday: Boolean = false,
    val friday: Boolean = false,
    val saturday: Boolean = false,
    val sunday: Boolean = false,
    val notes: String
)