package com.built4life.built4life2.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "workouts", indices = [
    Index(value = ["title"]), // Index on title
    Index(value = ["favorite"]), // Index on favorite
    // Or a composite index which might be more optimal for this specific query:
    // Index(value = ["favorite", "title"])
])
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Int = 0,
    val title: String,
    val description: String,
    val reps: String,
    val weight: String,
    val rounds: String,
    val minutes: String,
    val seconds: String,
    val distance: String,
    val firstSet: String,
    val secondSet: String,
    val thirdSet: String,
    val beginner: String,
    val novice: String,
    val intermediate: String,
    val advanced: String,
    val elite: String,
    val category: String,
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
