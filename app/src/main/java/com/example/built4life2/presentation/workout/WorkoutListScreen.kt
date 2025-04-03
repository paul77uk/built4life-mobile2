package com.example.built4life2.presentation.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.built4life2.R
import com.example.built4life2.customcomposables.PRDialog
import com.example.built4life2.customcomposables.SearchField
import com.example.built4life2.customcomposables.WorkoutCard
import com.example.built4life2.customcomposables.WorkoutFormDialog
import com.example.built4life2.data.Workout
import com.example.built4life2.designsystem.component.button.B4LButton
import com.example.built4life2.designsystem.component.button.ButtonType
import com.example.built4life2.ui.ViewModelProvider
import com.example.built4life2.ui.navigation.NavigationDestination
import com.example.built4life2.ui.viewmodels.WorkoutViewModel
import kotlinx.coroutines.launch

object WorkoutListDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.workout_list_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(
    viewModel: WorkoutViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val workoutListState by viewModel.workoutListUiState.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val workoutFormUiState = viewModel.workoutFormUiState
    val openDialog = remember { mutableStateOf(false) }
    val openInfoDialog = remember { mutableStateOf(false) }
    val isEdit = remember { mutableStateOf(false) }
    val showDeleteConfirmation = remember { mutableStateOf(false) }
    val showPRDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val onSearchTextChanged: (String) -> Unit = { query ->
        searchText = query
        viewModel.searchWorkout(query)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(WorkoutListDestination.titleRes)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog.value = true
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.create_new_workout)
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SearchField(
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged
            )
            LazyColumn {
                items(items = workoutListState.workoutList, key = { it.id }) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onEditClick = {
                            openDialog.value = true
                            isEdit.value = true
                            coroutineScope.launch {
                                workoutFormUiState.workout = workout
                                viewModel.updateUiState(workout)
                            }
                        },
                        onDeleteClick = {
                            showDeleteConfirmation.value = true
                            workoutFormUiState.workout = workout
                            viewModel.updateUiState(workout)
                        },
                        onPrClick = {
                            showPRDialog.value = true
                            isEdit.value = true
                            coroutineScope.launch {
                                workoutFormUiState.workout = workout
                                viewModel.updateUiState(workout)
                            }
                        },
                        onInfoClick = {
                            openInfoDialog.value = true
                            workoutFormUiState.workout = workout
                            viewModel.updateUiState(workout)
                        },
                    )
                }
            }
            if (openDialog.value) {
                WorkoutFormDialog(
                    onDismiss = {
                        coroutineScope.launch {
                            viewModel.updateUiState(
                                Workout(
                                    title = "",
                                    description = "",
                                    workoutDetails = "",
                                    reps = "",
                                    distance = "",
                                    weight = "",
                                    info = "",
                                    notes = ""
                                )
                            )
                            openDialog.value = false
                            isEdit.value = false
                        }
                    },
                    onSaveClick = {
                        if (isEdit.value) {
                            coroutineScope.launch {
                                viewModel.updateWorkout()
                                viewModel.updateUiState(
                                    Workout(
                                        title = "",
                                        description = "",
                                        workoutDetails = "",
                                        reps = "",
                                        distance = "",
                                        weight = "",
                                        info = "",
                                        notes = ""
                                    )
                                )
                                openDialog.value = false
                                isEdit.value = false
                            }
                        } else
                            coroutineScope.launch {
                                viewModel.saveWorkout()
                                viewModel.updateUiState(
                                    Workout(
                                        title = "",
                                        description = "",
                                        workoutDetails = "",
                                        reps = "",
                                        distance = "",
                                        weight = "",
                                        info = "",
                                        notes = ""
                                    )
                                )
                                openDialog.value = false
                                isEdit.value = false
                            }
                    },
                    workoutFormUiState = workoutFormUiState,
                    onValueChange = viewModel::updateUiState,
                    isEdit = isEdit.value
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
                                viewModel.updateUiState(
                                    Workout(
                                        title = "",
                                        description = "",
                                        workoutDetails = "",
                                        reps = "",
                                        distance = "",
                                        weight = "",
                                        info = "",
                                        notes = ""
                                    )
                                )
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
                                    viewModel.deleteWorkout()
                                    viewModel.updateUiState(
                                        Workout(
                                            title = "",
                                            description = "",
                                            workoutDetails = "",
                                            reps = "",
                                            distance = "",
                                            weight = "",
                                            info = "",
                                            notes = ""
                                        )
                                    )
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
                        workoutFormUiState.workout = Workout(
                            title = "",
                            description = "",
                            workoutDetails = "",
                            reps = "",
                            distance = "",
                            weight = "",
                            info = "",
                            notes = ""
                        )
                    },
                    onValueChange = viewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateWorkout()
                        }
                        showPRDialog.value = false
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
        }
    }
}

@Composable
fun InfoDialog(
    modifier: Modifier = Modifier,
    description: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = "Description") },
        text = {
            if (description.isEmpty()) {
                Text(text = "No description added")
            }
            Text(text = description)
        },
        onDismissRequest = onDismissRequest,
        dismissButton = {
            B4LButton(
                onClick = onDismissRequest,
                text = "Close",
                type = ButtonType.OUTLINE
            )
        },
        confirmButton = {

        },
        modifier = modifier
    )
}