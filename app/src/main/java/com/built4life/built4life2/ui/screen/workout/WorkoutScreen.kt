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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.built4life.built4life2.ui.components.B4LAppBar
import com.built4life.built4life2.ui.components.B4LButton
import com.built4life.built4life2.ui.components.ButtonType
import com.built4life.built4life2.ui.components.DailyDialog
import com.built4life.built4life2.ui.components.InfoDialog
import com.built4life.built4life2.ui.components.PRDialog
import com.built4life.built4life2.ui.components.SearchField
import com.built4life.built4life2.ui.components.WorkoutCard
import com.built4life.built4life2.ui.components.WorkoutFormDialog
import com.built4life.built4life2.ui.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel
) {
    LaunchedEffect(key1 = true) {
        workoutViewModel.getAllWorkouts()
        workoutViewModel.searchTextState.value = ""
    }
//    val workoutListState by workoutViewModel.workoutListUiState.collectAsState()
    val allWorkouts by workoutViewModel.allWorkouts.collectAsStateWithLifecycle()
//    val searchedWorkouts by workoutViewModel.searchedWorkouts.collectAsStateWithLifecycle()
    var searchText by remember { mutableStateOf("") }
    val searchTextState by workoutViewModel.searchTextState
    val workoutFormUiState = workoutViewModel.workoutFormUiState
    val openDialog = remember { mutableStateOf(false) }
    val openInfoDialog = remember { mutableStateOf(false) }
    val isEdit = remember { mutableStateOf(false) }
    val showDeleteConfirmation = remember { mutableStateOf(false) }
    val showPRDialog = remember { mutableStateOf(false) }
    val showDailyDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

//    val onSearchTextChanged: (String) -> Unit = { query ->
//        workoutViewModel.searchTextState.value = query
//        workoutViewModel.getSearchedWorkouts()
//    }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
        topBar = {
            B4LAppBar(
                onClick = {
                    openDialog.value = true
                    isEdit.value = false
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

            SearchField(
                searchText = searchTextState,
                onSearchTextChanged = {
                    workoutViewModel.searchTextState.value = it
                    workoutViewModel.getSearchedWorkouts()
                },
                modifier = Modifier.background(
                    color = Color.Black
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = allWorkouts, key = { it.workoutId }) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onEditClick = {
                            openDialog.value = true
                            isEdit.value = true
                            coroutineScope.launch {
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            }
                        },
                        onDeleteClick = {
                            showDeleteConfirmation.value = true
                            workoutFormUiState.workout = workout
                            workoutViewModel.updateUiState(workout)
                        },
                        onPrClick = {
                            showPRDialog.value = true
                            isEdit.value = true
                            coroutineScope.launch {
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(workout)
                            }
                        },
                        onInfoClick = {
                            openInfoDialog.value = true
                            workoutFormUiState.workout = workout
                            workoutViewModel.updateUiState(workout)
                        },
                        onFavoriteClick = {
                            coroutineScope.launch {
                                workoutFormUiState.workout = workout
                                workoutViewModel.updateUiState(
                                    workout.copy(
                                        favorite = !workout.favorite,
                                        favoriteOrder = LocalDateTime.now().toString()
                                    )
                                )
                                workoutViewModel.updateWorkout()
                                workoutViewModel.refreshUiState()
                            }
                        },
                        onDailyClick = {
                            showDailyDialog.value = true
                            workoutFormUiState.workout = workout
                            workoutViewModel.updateUiState(workout)
                        }
                    )
                }
            }
            if (openDialog.value) {
                WorkoutFormDialog(
                    onDismiss = {
                        coroutineScope.launch {
                            workoutViewModel.refreshUiState()
                            openDialog.value = false
                            isEdit.value = false
                        }
                    },
                    onSaveClick = {
                        if (isEdit.value) {
                            coroutineScope.launch {
                                workoutViewModel.updateWorkout()
                                workoutViewModel.refreshUiState()
                                openDialog.value = false
                                isEdit.value = true
                            }
                        } else
                            coroutineScope.launch {
                                workoutViewModel.saveWorkout()
                                workoutViewModel.refreshUiState()
                                openDialog.value = false
                                isEdit.value = false
                            }
                    },
                    workoutFormUiState = workoutFormUiState,
                    onValueChange = workoutViewModel::updateUiState,
                    isEdit = isEdit.value,
                )
            }
            if (showDeleteConfirmation.value) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteConfirmation.value = false
                        isEdit.value = false
                    },
                    title = {
                        Text(text = "Delete Workout")
                    },
                    text = {
                        Text(text = "Are you sure you want to delete this workout?")
                    },
                    dismissButton = {
                        B4LButton(
                            onClick = {
                                workoutViewModel.refreshUiState()
                                showDeleteConfirmation.value = false
                                isEdit.value = false
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
                                    workoutViewModel.refreshUiState()
                                    isEdit.value = false
                                    showDeleteConfirmation.value = false
                                }
                            },
                            text = "Delete"
                        )
                    }
                )
            }
            if (showPRDialog.value) {
                PRDialog(
                    onDismissRequest = {
                        showPRDialog.value = false
                        workoutViewModel.refreshUiState()
                    },
                    onValueChange = workoutViewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onClick = {
                        coroutineScope.launch {
                            workoutViewModel.updateWorkout()
                            workoutViewModel.refreshUiState()
                            isEdit.value = true
                            showPRDialog.value = false
                        }

                    }
                )
            }
            if (openInfoDialog.value) {
                InfoDialog(
                    description = workoutFormUiState.workout.description,
                    onDismissRequest = {
                        openInfoDialog.value = false
                    },
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
                        coroutineScope.launch {
                            workoutViewModel.updateWorkout()
                            showDailyDialog.value = false
                            workoutViewModel.refreshUiState()
                        }
                    }
                )
            }
        }
    }
}