package com.built4life.built4life2.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.built4life.built4life2.data.entity.Workout
import com.built4life.built4life2.data.repository.WorkoutRepository
import com.built4life.built4life2.ui.screen.workout.WorkoutFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel @Inject constructor(private val workoutRepository: WorkoutRepository) :
    ViewModel() {
    val options = listOf<String>(
        "All Workouts",
        "Bodyweight",
        "Crossfit WOD",
        "Legs",
        "Back",
        "Chest",
        "Shoulders",
        "Biceps",
        "Triceps",
        "Core"
    )

    private val _selectedOption: MutableState<String> = mutableStateOf(options[0])
    val selectedOption: MutableState<String> = _selectedOption

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: MutableState<String> = _searchTextState
    private val _categoryTextState: MutableState<String> = mutableStateOf("All Workouts")
    val categoryTextState: MutableState<String> = _categoryTextState
    val favoriteTextState: MutableState<String> = mutableStateOf("")

    private val _isWorkoutFormDialogOpen = MutableStateFlow(false)
    val isWorkoutFormDialogOpen: StateFlow<Boolean> = _isWorkoutFormDialogOpen.asStateFlow()

    private val _isPRDialogOpen = MutableStateFlow(false)
    val isPRDialogOpen: StateFlow<Boolean> = _isPRDialogOpen.asStateFlow()

    private val _isEditingWorkout = MutableStateFlow(false)
    val isEditingWorkout: StateFlow<Boolean> = _isEditingWorkout.asStateFlow()

    private val _expanded: MutableState<Boolean> = mutableStateOf(false)
    val expanded: MutableState<Boolean> = _expanded

    private val _moreOptionsExpanded: MutableState<Boolean> = mutableStateOf(false)
    val moreOptionsExpanded: MutableState<Boolean> = _moreOptionsExpanded

    fun changeMoreOptionsExpandedState() {
        _moreOptionsExpanded.value = !_moreOptionsExpanded.value
    }

    fun closePRDialog() {
        _isPRDialogOpen.value = false
        refreshUiState()
    }

    fun onPRSave() {
        updateWorkout()
        closePRDialog()
    }

    fun onLogScoreClick(workout: Workout) {
        _isPRDialogOpen.value = true
        updateUiState(workout)
    }

    fun onOptionSelected(option: String) {
        _selectedOption.value = option
        getSearchedWorkouts()
        changeExpandedState()
        setSearchTextState("")
//        refreshUiState()
    }

    fun changeExpandedState() {
        _expanded.value = !_expanded.value
    }

    fun openWorkoutFormDialog(isEdit: Boolean) {
        _isEditingWorkout.value = isEdit
        _isWorkoutFormDialogOpen.value = true
    }

    fun closeWorkoutFormDialog() {
        _isWorkoutFormDialogOpen.value = false
        refreshUiState()
    }

    fun onSave() {
        if (isEditingWorkout.value == true) {
            updateWorkout()
        } else {
            saveWorkout()
        }
        closeWorkoutFormDialog()
    }

    fun setSearchTextState(searchText: String) {
        _searchTextState.value = searchText
        getSearchedWorkouts()
    }

    fun setCategoryTextState(categoryText: String) {
        _categoryTextState.value = categoryText
    }


//    private val _searchedWorkouts = MutableStateFlow<List<Workout>>(emptyList())
//    val searchedWorkouts: StateFlow<List<Workout>> = _searchedWorkouts

    private val _allWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val allWorkouts: StateFlow<List<Workout>> = _allWorkouts

    private val _favoriteWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val favoriteWorkouts: StateFlow<List<Workout>> = _favoriteWorkouts

    private val _mondayWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val mondayWorkouts: StateFlow<List<Workout>> = _mondayWorkouts

    private val _tuesdayWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val tuesdayWorkouts: StateFlow<List<Workout>> = _tuesdayWorkouts

    private val _wednesdayWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val wednesdayWorkouts: StateFlow<List<Workout>> = _wednesdayWorkouts

    private val _thursdayWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val thursdayWorkouts: StateFlow<List<Workout>> = _thursdayWorkouts

    private val _fridayWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val fridayWorkouts: StateFlow<List<Workout>> = _fridayWorkouts

    private val _saturdayWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val saturdayWorkouts: StateFlow<List<Workout>> = _saturdayWorkouts

    private val _sundayWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val sundayWorkouts: StateFlow<List<Workout>> = _sundayWorkouts

//    private val searchQuery = MutableStateFlow("")

//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    val workoutListUiState: StateFlow<WorkoutListUiState> = searchQuery
//        .debounce(300)
//        .flatMapLatest { query ->
//            workoutRepository.getAllWorkouts()
//                .map { workouts ->
//                    if (query.isBlank()) {
//                        WorkoutListUiState(workouts)
//                    } else {
//                        val filteredWorkouts = workouts.filter {
//                            it.matchesQuery(query)
//                        }
//                        WorkoutListUiState(filteredWorkouts)
//                    }
//                }
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = WorkoutListUiState()
//        )

    fun getSearchedWorkouts() = viewModelScope.launch {
        if (_searchTextState.value.isBlank() && _selectedOption.value == "All Workouts")
            getAllWorkouts()
        else if (_selectedOption.value != "All Workouts")
            getWorkoutsByCategory()
        if (_searchTextState.value.isNotBlank() && _selectedOption.value == "All Workouts")
            getSearchedWorkoutsByTitle()
    }

    fun getWorkoutsByCategory() = viewModelScope.launch {
        workoutRepository.getWorkoutsByCategory(_selectedOption.value).collect {
            _allWorkouts.value = it
        }

    }

    fun getSearchedWorkoutsByTitle() = viewModelScope.launch {
        workoutRepository.searchWorkoutsByTitle(searchTextState.value).collect {
            _allWorkouts.value = it
        }
    }

    fun getFavoriteSearchedWorkouts() = viewModelScope.launch {
        if (favoriteTextState.value.isBlank())
            getFavoriteWorkouts()
        else
            workoutRepository.searchFavoriteWorkoutsByTitle(favoriteTextState.value).collect {
                _favoriteWorkouts.value = it
            }
    }

    fun getAllWorkouts() = viewModelScope.launch {
        workoutRepository.getAllWorkouts.collect {
            _allWorkouts.value = it
        }
    }

    fun getFavoriteWorkouts() = viewModelScope.launch {
        workoutRepository.getFavoriteWorkouts.collect {
            _favoriteWorkouts.value = it
        }
    }

    fun getMondayWorkouts() = viewModelScope.launch {
        workoutRepository.getMondayWorkouts.collect {
            _mondayWorkouts.value = it
        }
    }

    fun getTuesdayWorkouts() = viewModelScope.launch {
        workoutRepository.getTuesdayWorkouts.collect {
            _tuesdayWorkouts.value = it
        }
    }

    fun getWednesdayWorkouts() = viewModelScope.launch {
        workoutRepository.getWednesdayWorkouts.collect {
            _wednesdayWorkouts.value = it
        }
    }

    fun getThursdayWorkouts() = viewModelScope.launch {
        workoutRepository.getThursdayWorkouts.collect {
            _thursdayWorkouts.value = it
        }
    }

    fun getFridayWorkouts() = viewModelScope.launch {
        workoutRepository.getFridayWorkouts.collect {
            _fridayWorkouts.value = it
        }
    }

    fun getSaturdayWorkouts() = viewModelScope.launch {
        workoutRepository.getSaturdayWorkouts.collect {
            _saturdayWorkouts.value = it
        }
    }

    fun getSundayWorkouts() = viewModelScope.launch {
        workoutRepository.getSundayWorkouts.collect {
            _sundayWorkouts.value = it
        }
    }

//
//fun searchWorkouts(query: String) {
//   viewModelScope.launch {
//       workoutRepository.searchWorkoutsByTitle(query).collect {
//           _allWorkouts.value = it
//       }
//   }
//}

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
                category = "",
                favoriteOrder = ""
            ),
            isEntryValid = false
        )
    }

    fun saveWorkout() {
        viewModelScope.launch {
            if (validateInput()) {
                workoutRepository.insertWorkout(workoutFormUiState.workout)
            }
        }
    }

    fun updateWorkout() {
        viewModelScope.launch {
            if (validateInput(workoutFormUiState.workout)) {
                workoutRepository.updateWorkout(workoutFormUiState.workout)
            }
        }
    }

    fun deleteWorkout() {
        viewModelScope.launch {
            workoutRepository.deleteWorkout(workoutFormUiState.workout)
        }
    }

    private fun validateInput(workout: Workout = workoutFormUiState.workout): Boolean {
        return with(workout) {
            title.isNotBlank()
            category.isNotBlank()
        }
    }
}

fun Workout.matchesQuery(query: String): Boolean {
    return title.contains(query, ignoreCase = true) || description.contains(
        query,
        ignoreCase = true
    )
}