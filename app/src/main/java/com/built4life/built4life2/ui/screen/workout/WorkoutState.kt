package com.built4life.built4life2.ui.screen.workout

import com.built4life.built4life2.data.entity.Workout

data class WorkoutListUiState(val workoutList: List<Workout> = listOf())
data class WorkoutFormUiState(
    var workout: Workout = Workout(
        title = "",
        description = "",
        reps = "",
        weight = "",
        rounds = "",
        minutes = "",
        seconds = "",
        distance = "",
        firstSet = "",
        secondSet = "",
        thirdSet = "",
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
        favoriteOrder = ""
    ),
    val isEntryValid: Boolean = false
)
