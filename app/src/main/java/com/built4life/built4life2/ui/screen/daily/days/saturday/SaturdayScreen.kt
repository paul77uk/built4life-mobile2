package com.built4life.built4life2.ui.screen.daily.days.saturday

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.built4life.built4life2.ui.components.B4LButton
import com.built4life.built4life2.ui.components.ButtonType
import com.built4life.built4life2.ui.components.DailyDialog
import com.built4life.built4life2.ui.components.InfoDialog
import com.built4life.built4life2.ui.components.MoreOptionsDropdown
import com.built4life.built4life2.ui.components.PRDialog
import com.built4life.built4life2.ui.components.PRTypeDialog
import com.built4life.built4life2.ui.components.StrengthLevelDialog
import com.built4life.built4life2.ui.components.WorkoutCard
import com.built4life.built4life2.ui.components.WorkoutFormDialog
import com.built4life.built4life2.ui.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SaturdayScreen(
    workoutViewModel: WorkoutViewModel
) {

    LaunchedEffect(key1 = true) {
        workoutViewModel.getSaturdayWorkouts()
    }

    val saturdayWorkouts by workoutViewModel.saturdayWorkouts.collectAsStateWithLifecycle()
    val workoutFormUiState = workoutViewModel.workoutFormUiState
    var isEditMode by remember { mutableStateOf(false) }
    var showWorkoutFormDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var showPRDialog by remember { mutableStateOf(false) }
    var showDailyDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val openPRTypeDialog = remember { mutableStateOf(false) }
    val openLevelDialog = remember { mutableStateOf(false) }

    // Effect to refresh the dialogs' state
    LaunchedEffect(showWorkoutFormDialog,showPRDialog,showDailyDialog,showInfoDialog,showDeleteConfirmationDialog) {
        if (!showWorkoutFormDialog && !showPRDialog && !showDailyDialog && !showInfoDialog && !showDeleteConfirmationDialog)
        {
            workoutViewModel.refreshUiState()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = MaterialTheme.colorScheme.surfaceContainer),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = saturdayWorkouts,
                    key = { it.workoutId }
                ) { workout ->
                    WorkoutCard(
                        workout = workout,
//                        onMoreVertClick = {
//                            // open more options dropdown
//                            workoutViewModel.changeMoreOptionsExpandedState()
//                        },
                        isLoggedScore =
                            // whether a workout score has been entered
                            false,
                        onLogScoreClick = {
                            // if score is not already lo
                        },
//                        expanded = moreOptionsExpanded,
                    ){
                        MoreOptionsDropdown(
                            onEditClick = {},
                            onDeleteClick ={},
                            onInfoClick = {},
                            onPrTypeClick = {},
                            onLevelClick = {},
                            onFavoriteClick = {},
                            onDailyClick = {},
                            isFavorite = true,
                            isDelete = true,
                            isReps = true,
                        )
                    }
                }
            }

            // Workout Form Dialog
            if (showWorkoutFormDialog) {
                WorkoutFormDialog(
                    onDismiss = {
                        showWorkoutFormDialog = false
                        isEditMode = false
                    },
                    onSaveClick = {
                        coroutineScope.launch {
                            if (isEditMode) {
                                workoutViewModel.updateWorkout()
                            } else {
                                workoutViewModel.saveWorkout()
                            }
                            showWorkoutFormDialog = false
                            isEditMode = false
                        }
                    },
                    workoutFormUiState = workoutFormUiState,
                    onValueChange = workoutViewModel::updateUiState,
                    isEdit = isEditMode,
                )
            }
            // Delete Confirmation Dialog
            if (showDeleteConfirmationDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteConfirmationDialog = false
                        isEditMode = false
                    },
                    title = { Text(text = "Delete Workout") },
                    text = { Text(text = "Are you sure you want to delete this workout?") },
                    dismissButton = {
                        B4LButton(
                            onClick = {
                                showDeleteConfirmationDialog = false
                                isEditMode = false
                            },
                            text = "Cancel",
                            type = ButtonType.OUTLINE
                        )
                    },
                    confirmButton = {
                        B4LButton(
                            onClick = {
                                coroutineScope.launch {
                                    workoutViewModel.deleteWorkout()
                                    showDeleteConfirmationDialog = false
                                    isEditMode = false
                                }
                            },
                            text = "Delete"
                        )
                    }
                )
            }

            if (openPRTypeDialog.value) {
                PRTypeDialog(
                    onValueChange = workoutViewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onDismissRequest = {
                        openPRTypeDialog.value = false
                    },
                    onConfirm = {
                        coroutineScope.launch {
                            workoutViewModel.updateWorkout()
                            openPRTypeDialog.value = false
                        }
                    }
                )
            }

            if (openLevelDialog.value) {
                StrengthLevelDialog(
                    onValueChange = workoutViewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onDismissRequest = {
                        openLevelDialog.value = false
                    },
                    onConfirm = {
                        coroutineScope.launch {
                            workoutViewModel.updateWorkout()
                            openLevelDialog.value = false
                        }
                    }
                )
            }

            // PR Dialog
            if (showPRDialog) {
                PRDialog(
                    onDismissRequest = {
                        showPRDialog = false
                        isEditMode = false
                    },
                    onValueChange = workoutViewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onClick = {
                        coroutineScope.launch {
                            workoutViewModel.updateWorkout()
                            showPRDialog = false
                            isEditMode = false
                        }
                    }
                )
            }
            // Info Dialog
            if (showInfoDialog) {
                InfoDialog(
                    description = workoutFormUiState.workout.description,
                    onDismissRequest = {
                        showInfoDialog = false
                    },
                )
            }
            // Daily Dialog
            if (showDailyDialog) {
                DailyDialog(
                    onDismissRequest = {
                        showDailyDialog = false
                    },
                    workoutFormUiState = workoutFormUiState,
                    onValueChange = workoutViewModel::updateUiState,
                    onConfirm = {
                        coroutineScope.launch {
                            workoutViewModel.updateWorkout()
                            showDailyDialog = false
                        }
                    }
                )
            }
        }
    }
}