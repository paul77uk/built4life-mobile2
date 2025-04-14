package com.example.built4life2.domain.model

data class Workout(
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
    val favorite: Boolean = false,
    val notes: String,
)
