package com.built4life.built4life2.presentation.daily.days.monday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.built4life.built4life2.data.WorkoutDao
import com.built4life.built4life2.presentation.workout.WorkoutListUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MondayViewModel(private val workoutDao: WorkoutDao) : ViewModel() {


    val workoutListUiState: StateFlow<WorkoutListUiState> =
        workoutDao.getMondayWorkouts().map { WorkoutListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = WorkoutListUiState()
            )

}