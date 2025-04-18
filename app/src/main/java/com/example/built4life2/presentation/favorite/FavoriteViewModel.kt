package com.example.built4life2.presentation.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.built4life2.data.Workout
import com.example.built4life2.data.WorkoutDao
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
    val workoutListUiState: StateFlow<FavoriteListUiState> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            workoutDao.getFavoriteWorkouts()
                .map { workouts ->
                    if (query.isBlank()) {
                        FavoriteListUiState(workouts)
                    } else {
                        val filteredWorkouts = workouts.filter {
                            it.matchesQuery(query)
                        }
                        FavoriteListUiState(filteredWorkouts)
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FavoriteListUiState()
        )

    fun searchWorkout(query: String) {
        searchQuery.value = query
    }

    var workoutFormUiState by mutableStateOf(FavoriteFormUiState())

    fun updateUiState(workout: Workout) {
        workoutFormUiState = FavoriteFormUiState(
            workout = workout,
            isEntryValid = validateInput(workout)
        )
    }

//    suspend fun saveWorkout() {
//        if (validateInput()) {
//            workoutDao.insert(workoutFormUiState.workout)
//        }
//    }

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

data class FavoriteListUiState(val workoutList: List<Workout> = listOf())
data class FavoriteFormUiState(
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
        notes = "",
    ),
    val isEntryValid: Boolean = false
)

fun Workout.matchesQuery(query: String): Boolean {
    return title.contains(query, ignoreCase = true) || description.contains(
        query,
        ignoreCase = true
    )
}