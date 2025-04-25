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
        notes = "",
        mondayOrder = "",
        tuesdayOrder = "",
        wednesdayOrder = "",
        thursdayOrder = "",
        fridayOrder = "",
        saturdayOrder = "",
        sundayOrder = "",
        prType = "Reps",
    ),
    val isEntryValid: Boolean = false
)
