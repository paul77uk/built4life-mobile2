package com.built4life.built4life2.presentation.workout

import com.built4life.built4life2.data.Workout

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
        favoriteOrder = "",
        prType = "Reps",
    ),
    val isEntryValid: Boolean = false
)
