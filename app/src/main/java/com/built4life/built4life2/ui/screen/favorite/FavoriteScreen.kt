package com.built4life.built4life2.ui.screen.favorite

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.built4life.built4life2.ui.components.DailyDialog
import com.built4life.built4life2.ui.components.InfoDialog
import com.built4life.built4life2.ui.components.MoreOptionsDropdown
import com.built4life.built4life2.ui.components.PRDialog
import com.built4life.built4life2.ui.components.PRTypeDialog
import com.built4life.built4life2.ui.components.SearchField
import com.built4life.built4life2.ui.components.StrengthLevelDialog
import com.built4life.built4life2.ui.components.WorkoutCard
import com.built4life.built4life2.ui.components.WorkoutFormDialog
import com.built4life.built4life2.ui.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FavoriteScreen(
    workoutViewModel: WorkoutViewModel
) {

    LaunchedEffect(key1 = true) {
        workoutViewModel.getFavoriteWorkouts()
        workoutViewModel.favoriteTextState.value = ""
    }

    val favoriteWorkouts by workoutViewModel.favoriteWorkouts.collectAsStateWithLifecycle()
    val isWorkoutFormDialogOpen by workoutViewModel.isWorkoutFormDialogOpen.collectAsStateWithLifecycle()
    val isEdit by workoutViewModel.isEditingWorkout.collectAsStateWithLifecycle()
    val searchTextState by workoutViewModel.searchTextState
    val workoutFormUiState = workoutViewModel.workoutFormUiState
    var expanded by workoutViewModel.expanded
    var selectedOption by workoutViewModel.selectedOption
    val options = workoutViewModel.options
    var searchText by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val openInfoDialog = remember { mutableStateOf(false) }
    val showDeleteConfirmation = remember { mutableStateOf(false) }
    val showPRDialog = remember { mutableStateOf(false) }
    val showDailyDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val openPRTypeDialog = remember { mutableStateOf(false) }
    val openLevelDialog = remember { mutableStateOf(false) }

//    val onSearchTextChanged: (String) -> Unit = { query ->
//        searchText = query
//        viewModel.searchWorkout(query)
//    }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Favorites".uppercase(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
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
                    workoutViewModel.favoriteTextState.value = it
                    workoutViewModel.getFavoriteSearchedWorkouts()
                },
                modifier = Modifier.background(
                    color = Color.Black
                ).padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = favoriteWorkouts, key = { it.workoutId }) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onLogScoreClick = {
                            // open score dialog
                            showPRDialog.value = true
                            workoutFormUiState.workout = workout
                            workoutViewModel.updateUiState(workout)
                        },
                    ) {
                        MoreOptionsDropdown(
                            onEditClick = {
                                workoutViewModel.openWorkoutFormDialog(isEdit = true)
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
                            isReps = workout.prType == "Reps",
                        )
                    }
                }
            }

            WorkoutFormDialog(
                isOpen = isWorkoutFormDialogOpen,
                onDismiss = {
                    workoutViewModel.closeWorkoutFormDialog()
                },
                onSaveClick = {
                    workoutViewModel.onSave()
                },
                workoutFormUiState = workoutFormUiState,
                onValueChange = workoutViewModel::updateUiState,
                isEdit = isEdit,
            )
//            if (showDeleteConfirmation.value) {
//                AlertDialog(
//                    onDismissRequest = {
//                        showDeleteConfirmation.value = false
//                        isEdit.value = false
//                    },
//                    title = {
//                        Text(text = "Delete Workout")
//                    },
//                    text = {
//                        Text(text = "Are you sure you want to delete this workout?")
//                    },
//                    dismissButton = {
//                        B4LButton(
//                            onClick = {
//                                viewModel.refreshUiState()
//                                showDeleteConfirmation.value = false
//                                isEdit.value = false
//                            },
//                            text = "Cancel",
//                            type = ButtonType.OUTLINE
//                        )
//                    },
//                    confirmButton = {
//                        B4LButton(
//                            onClick = {
//                                coroutineScope.launch {
//                                    viewModel.deleteWorkout()
//                                    viewModel.refreshUiState()
//                                    isEdit.value = false
//                                    showDeleteConfirmation.value = false
//                                }
//                            },
//                            text = "Delete"
//                        )
//                    }
//                )
//            }

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
//                            isEdit = true
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