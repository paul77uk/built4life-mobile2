package com.example.built4life2.presentation.daily.days.tuesday

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.built4life2.data.Workout
import com.example.built4life2.data.WorkoutDao
import com.example.built4life2.presentation.workout.WorkoutFormUiState
import com.example.built4life2.presentation.workout.WorkoutListUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TuesdayViewModel(private val workoutDao: WorkoutDao) : ViewModel() {

    val workoutListUiState: StateFlow<WorkoutListUiState> =
        workoutDao.getTuesdayWorkouts().map { WorkoutListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = WorkoutListUiState()
            )


    var workoutFormUiState by mutableStateOf(WorkoutFormUiState())

    fun updateUiState(workout: Workout) {
        workoutFormUiState = WorkoutFormUiState(
            workout = workout,
            isEntryValid = validateInput(workout)
        )
    }

    fun refreshUiState() {
        workoutFormUiState = WorkoutFormUiState(
            workout = Workout(
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
                mondayOrder = "",
                tuesdayOrder = "",
                wednesdayOrder = "",
                thursdayOrder = "",
                fridayOrder = "",
                saturdayOrder = "",
                sundayOrder = "",
                prType = "",
                favoriteOrder = "",
            ),
            isEntryValid = false
        )
    }

    suspend fun saveWorkout() {
        if (validateInput()) {
            workoutDao.insert(workoutFormUiState.workout)
        }
    }

    suspend fun updateWorkout() {
        if (validateInput(workoutFormUiState.workout)) {
            workoutDao.update(workoutFormUiState.workout)
        }
    }

    suspend fun deleteWorkout() {
        workoutDao.delete(workoutFormUiState.workout)
    }

    private fun validateInput(workout: Workout = workoutFormUiState.workout): Boolean {
        return with(workout) {
            title.isNotBlank()
        }
    }
}