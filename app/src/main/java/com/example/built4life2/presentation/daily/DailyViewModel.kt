package com.example.built4life2.presentation.daily

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.built4life2.data.Workout
import com.example.built4life2.data.WorkoutDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class DailyViewModel(private val workoutDao: WorkoutDao) : ViewModel() {

    val days = listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")
    private val today = LocalDate.now().dayOfWeek.toString()
    var dayIndex: StateFlow<Int> = MutableStateFlow(days.indexOf(today))


    val workoutListUiState: StateFlow<WorkoutListUiState> =
        when (days[dayIndex.value]) {
            "MONDAY" -> workoutDao.getMondayWorkouts().map { WorkoutListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkoutListUiState()
                )

            "TUESDAY" -> workoutDao.getTuesdayWorkouts().map { WorkoutListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkoutListUiState()
                )

            "WEDNESDAY" -> workoutDao.getWednesdayWorkouts().map { WorkoutListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkoutListUiState()
                )

            "THURSDAY" -> workoutDao.getThursdayWorkouts().map { WorkoutListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkoutListUiState()
                )

            "FRIDAY" -> workoutDao.getFridayWorkouts().map { WorkoutListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkoutListUiState()
                )

            "SATURDAY" -> workoutDao.getSaturdayWorkouts().map { WorkoutListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkoutListUiState()
                )

            "SUNDAY" -> workoutDao.getSundayWorkouts().map { WorkoutListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkoutListUiState()
                )

            else -> {
                workoutDao.getMondayWorkouts().map { WorkoutListUiState(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = WorkoutListUiState()
                    )
            }
        }

    var workoutFormUiState by mutableStateOf(WorkoutFormUiState())

    fun getSundayWorkouts(): StateFlow<WorkoutListUiState> {
        return workoutDao.getSundayWorkouts().map { WorkoutListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = WorkoutListUiState()
            )
    }

    fun updateUiState(workout: Workout) {
        workoutFormUiState = WorkoutFormUiState(
            workout = workout,
            isEntryValid = validateInput(workout)
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

