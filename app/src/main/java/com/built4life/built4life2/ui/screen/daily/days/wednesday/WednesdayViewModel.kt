package com.built4life.built4life2.ui.screen.daily.days.wednesday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.built4life.built4life2.data.dao.WorkoutDao
import com.built4life.built4life2.ui.screen.workout.WorkoutListUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class WednesdayViewModel(private val workoutDao: WorkoutDao) : ViewModel() {

    val workoutListUiState: StateFlow<WorkoutListUiState> =
        workoutDao.getWednesdayWorkouts().map { WorkoutListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = WorkoutListUiState()
            )

}