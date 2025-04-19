package com.example.built4life2.presentation.workout

import com.example.built4life2.data.Workout

data class WorkoutListUiState(val workoutList: List<Workout> = listOf())
data class WorkoutFormUiState(
    var workout: Workout = Workout(
        title = "",
        description = "",
        firstSetReps = "",
        totalReps = "",
        weight = "",
        beginner = "",
        novice = "",
        intermediate = "",
        advanced = "",
        elite = "",
        favorite = false,
        monday = false,
        tuesday = false,
        wednesday = false,
        thursday = false,
        friday = false,
        saturday = false,
        notes = "",
    ),
    val isEntryValid: Boolean = false
)
