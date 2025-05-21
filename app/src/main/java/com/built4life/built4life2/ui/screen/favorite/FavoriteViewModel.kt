package com.built4life.built4life2.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.built4life.built4life2.data.dao.WorkoutDao
import com.built4life.built4life2.data.entity.Workout
import com.built4life.built4life2.ui.screen.workout.WorkoutListUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(private val workoutDao: WorkoutDao) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val workoutListUiState: StateFlow<WorkoutListUiState> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            workoutDao.getFavoriteWorkouts()
                .map { workouts ->
                    if (query.isBlank()) {
                        WorkoutListUiState(workouts)
                    } else {
                        val filteredWorkouts = workouts.filter {
                            it.matchesQuery(query)
                        }
                        WorkoutListUiState(filteredWorkouts)
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WorkoutListUiState()
        )

    fun searchWorkout(query: String) {
        searchQuery.value = query
    }
}

fun Workout.matchesQuery(query: String): Boolean {
    return title.contains(query, ignoreCase = true) || description.contains(
        query,
        ignoreCase = true
    )
}