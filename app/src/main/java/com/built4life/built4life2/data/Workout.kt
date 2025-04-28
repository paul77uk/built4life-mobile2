package com.built4life.built4life2.data

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
    val favoriteOrder: String,
    val monday: Boolean = false,
    val mondayOrder: String,
    val tuesday: Boolean = false,
    val tuesdayOrder: String,
    val wednesday: Boolean = false,
    val wednesdayOrder: String,
    val thursday: Boolean = false,
    val thursdayOrder: String,
    val friday: Boolean = false,
    val fridayOrder: String,
    val saturday: Boolean = false,
    val saturdayOrder: String,
    val sunday: Boolean = false,
    val sundayOrder: String,
    val prType: String = "Reps",
    val notes: String
)