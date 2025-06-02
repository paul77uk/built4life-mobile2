package com.built4life.built4life2.ui.screen.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.built4life.built4life2.ui.components.B4LAppBar
import com.built4life.built4life2.ui.components.DailyDialog
import com.built4life.built4life2.ui.components.DeleteConfirmationDialog
import com.built4life.built4life2.ui.components.InfoDialog
import com.built4life.built4life2.ui.components.MoreOptionsDropdown
import com.built4life.built4life2.ui.components.PRDialog
import com.built4life.built4life2.ui.components.PRTypeDialog
import com.built4life.built4life2.ui.components.SearchField
import com.built4life.built4life2.ui.components.StrengthLevelDialog
import com.built4life.built4life2.ui.components.WorkoutCard
import com.built4life.built4life2.ui.components.WorkoutFormDialog
import com.built4life.built4life2.ui.components.WorkoutsDropdownMenu
import com.built4life.built4life2.ui.viewmodel.WorkoutViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel
) {
    LaunchedEffect(key1 = true) {
        workoutViewModel.getSearchedWorkouts()
    }
    val allWorkouts by workoutViewModel.allWorkouts.collectAsStateWithLifecycle()
    val isWorkoutFormDialogOpen by workoutViewModel.isWorkoutFormDialogOpen.collectAsStateWithLifecycle()
    val isPRDialogOpen by workoutViewModel.isPRDialogOpen.collectAsStateWithLifecycle()
    val isEdit by workoutViewModel.isEditingWorkout.collectAsStateWithLifecycle()
    val searchTextState by workoutViewModel.searchTextState
    val workoutFormUiState = workoutViewModel.workoutFormUiState
    var expanded by workoutViewModel.expanded
    var selectedOption by workoutViewModel.selectedOption
    val options = workoutViewModel.options


    val openInfoDialog = remember { mutableStateOf(false) }
    val openPRTypeDialog = remember { mutableStateOf(false) }
    val openLevelDialog = remember { mutableStateOf(false) }
    val showDeleteConfirmation = remember { mutableStateOf(false) }
    val showDailyDialog = remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
        topBar = {
            B4LAppBar(
                onClick = {
                    workoutViewModel.openWorkoutFormDialog(isEdit = false)
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SearchField(
                    searchText = searchTextState,
                    onSearchTextChanged = { workoutViewModel.setSearchTextState(it) },
                )

                WorkoutsDropdownMenu(
                    options = options,
                    expanded = expanded,
                    onExpandedChange = { workoutViewModel.changeExpandedState() },
                    selectedOption = selectedOption,
                    onDismissRequest = { workoutViewModel.changeExpandedState() },
                    onClick = { workoutViewModel.onOptionSelected(it) }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = allWorkouts, key = { it.workoutId }) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onLogScoreClick = { workoutViewModel.onLogScoreClick(workout) },
                    ) {
                        MoreOptionsDropdown(
                            onEditClick = {
                                workoutViewModel.openWorkoutFormDialog(isEdit = true)
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            },
                            onDeleteClick = {
                                showDeleteConfirmation.value = true
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            },
                            onInfoClick = {
                                openInfoDialog.value = true
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            },
                            onPrTypeClick = {
                                openPRTypeDialog.value = true
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            },
                            onLevelClick = {
                                openLevelDialog.value = true
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            },
                            onFavoriteClick = {
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(
                                    workout.copy(
                                        favorite = !workout.favorite,
                                        favoriteOrder = LocalDateTime.now().toString()
                                    )
                                )
                                workoutViewModel.updateWorkout()
                                workoutViewModel.refreshUiState()
                            },
                            onDailyClick = {
                                showDailyDialog.value = true
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            },
                            isFavorite = workout.favorite,
                            isDelete = true,
                            isReps = workout.prType == "Reps",
                        )
                    }
                }
            }

            WorkoutFormDialog(
                isOpen = isWorkoutFormDialogOpen,
                onDismiss = { workoutViewModel.closeWorkoutFormDialog() },
                onSaveClick = { workoutViewModel.onSave() },
                workoutFormUiState = workoutFormUiState,
                onValueChange = workoutViewModel::updateUiState,
                isEdit = isEdit,
            )

            DeleteConfirmationDialog(
                isOpen = showDeleteConfirmation.value,
                onDismissRequest = {
                    showDeleteConfirmation.value = false
                    workoutViewModel.refreshUiState()
                },
                onConfirmClick = {
                    workoutViewModel.deleteWorkout()
                    workoutViewModel.refreshUiState()
                    showDeleteConfirmation.value = false
                }
            )

            if (isPRDialogOpen) {
                PRDialog(
                    onDismissRequest = { workoutViewModel.closePRDialog() },
                    onValueChange = workoutViewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onClick = { workoutViewModel.onPRSave() }
                )
            }
            if (openInfoDialog.value) {
                InfoDialog(
                    description = workoutFormUiState.workout.description,
                    onDismissRequest = {
                        openInfoDialog.value = false
                        workoutViewModel.refreshUiState()
                    },
                )
            }
            if (openPRTypeDialog.value) {
                PRTypeDialog(
                    onValueChange = workoutViewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onDismissRequest = {
                        openPRTypeDialog.value = false
                        workoutViewModel.refreshUiState()
                    },
                    onConfirm = {
                        workoutViewModel.updateWorkout()
                        openPRTypeDialog.value = false
                        workoutViewModel.refreshUiState()
//                            isEdit.value = true
                    }
                )
            }

            if (openLevelDialog.value) {
                StrengthLevelDialog(
                    onValueChange = workoutViewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onDismissRequest = {
                        openLevelDialog.value = false
                        workoutViewModel.refreshUiState()
                    },
                    onConfirm = {
                        workoutViewModel.updateWorkout()
                        openLevelDialog.value = false
                        workoutViewModel.refreshUiState()
//                            isEdit.value = true
                    }
                )
            }
            if (showDailyDialog.value) {
                DailyDialog(
                    onDismissRequest = {
                        showDailyDialog.value = false
                    },
                    workoutFormUiState = workoutFormUiState,
                    onValueChange = workoutViewModel::updateUiState,
                    onConfirm = {
                        workoutViewModel.updateWorkout()
                        showDailyDialog.value = false
                        workoutViewModel.refreshUiState()
                    }
                )
            }
        }
    }
}