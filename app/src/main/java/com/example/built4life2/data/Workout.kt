package com.example.built4life2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
    val notes: String
)