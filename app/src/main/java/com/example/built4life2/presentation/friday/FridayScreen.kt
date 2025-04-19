package com.example.built4life2.presentation.friday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.built4life2.customcomposables.PRDialog
import com.example.built4life2.customcomposables.WorkoutCard
import com.example.built4life2.data.Workout
import com.example.built4life2.presentation.components.B4LButton
import com.example.built4life2.presentation.components.ButtonType
import com.example.built4life2.presentation.ViewModelProvider
import com.example.built4life2.presentation.daily.WorkoutFormUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FridayScreen(
    viewModel: FridayViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val workoutListState by viewModel.workoutListUiState.collectAsState()
    val workoutFormUiState = viewModel.workoutFormUiState
    val openDialog = remember { mutableStateOf(false) }
    val openInfoDialog = remember { mutableStateOf(false) }
    val isEdit = remember { mutableStateOf(false) }
    val showDeleteConfirmation = remember { mutableStateOf(false) }
    val showPRDialog = remember { mutableStateOf(false) }
    val showDailyDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        "FRIDAY",
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 24.sp
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Black,
//                    titleContentColor = Color.White
//                )
//            )
//        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = workoutListState.workoutList, key = { it.workoutId }) { workout ->
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
                        onFavoriteClick = {
                            coroutineScope.launch {
                                workoutFormUiState.workout = workout
                                viewModel.updateUiState(workout.copy(favorite = !workout.favorite))
                                viewModel.updateWorkout()
                            }
                        },
                        onDailyClick = {
                            showDailyDialog.value = true
                            workoutFormUiState.workout = workout
                            viewModel.updateUiState(workout)
                        }
                    )
                }
            }
//            if (openDialog.value) {
//                WorkoutFormDialog(
//                    onDismiss = {
//                        coroutineScope.launch {
//                            viewModel.updateUiState(
//                                Workout(
//                                    title = "",
//                                    description = "",
//                                    firstSetReps = "",
//                                    totalReps = "",
//                                    weight = "",
//                                    beginner = "",
//                                    novice = "",
//                                    intermediate = "",
//                                    advanced = "",
//                                    elite = "",
//                                    favorite = false,
//                                    notes = ""
//                                )
//                            )
//                            openDialog.value = false
//                            isEdit.value = false
//                        }
//                    },
//                    onSaveClick = {
//                        if (isEdit.value) {
//                            coroutineScope.launch {
//                                viewModel.updateWorkout()
//                                viewModel.updateUiState(
//                                    Workout(
//                                        title = "",
//                                        description = "",
//                                        firstSetReps = "",
//                                        totalReps = "",
//                                        weight = "",
//                                        beginner = "",
//                                        novice = "",
//                                        intermediate = "",
//                                        advanced = "",
//                                        elite = "",
//                                        favorite = false,
//                                        notes = ""
//                                    )
//                                )
//                                openDialog.value = false
//                                isEdit.value = true
//                            }
//                        } else
//                            coroutineScope.launch {
//                                viewModel.saveWorkout()
//                                viewModel.updateUiState(
//                                    Workout(
//                                        title = "",
//                                        description = "",
//                                        firstSetReps = "",
//                                        totalReps = "",
//                                        weight = "",
//                                        beginner = "",
//                                        novice = "",
//                                        intermediate = "",
//                                        advanced = "",
//                                        elite = "",
//                                        favorite = false,
//                                        notes = ""
//                                    )
//                                )
//                                openDialog.value = false
//                                isEdit.value = false
//                            }
//                    },
//                    workoutFormUiState = workoutFormUiState,
//                    onValueChange = viewModel::updateUiState,
//                    isEdit = isEdit.value
//                )
//            }
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
                                        firstSetReps = "",
                                        totalReps = "",
                                        weight = "",
                                        beginner = "",
                                        novice = "",
                                        intermediate = "",
                                        advanced = "",
                                        elite = "",
                                        favorite = false,
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
                                            firstSetReps = "",
                                            totalReps = "",
                                            weight = "",
                                            beginner = "",
                                            novice = "",
                                            intermediate = "",
                                            advanced = "",
                                            elite = "",
                                            favorite = false,
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
                            firstSetReps = "",
                            totalReps = "",
                            weight = "",
                            beginner = "",
                            novice = "",
                            intermediate = "",
                            advanced = "",
                            elite = "",
                            favorite = false,
                            notes = ""
                        )
                    },
                    onValueChange = viewModel::updateUiState,
                    workoutDetails = workoutFormUiState.workout,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateWorkout()
                            viewModel.updateUiState(
                                Workout(
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
                                    notes = ""
                                )
                            )
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
//            if (showDailyDialog.value) {
//                DailyDialog(
//                    onDismissRequest = {
//                        showDailyDialog.value = false
//                    },
//                    workoutFormUiState = workoutFormUiState,
//                    onValueChange = viewModel::updateUiState,
//                    onConfirm = {
//                        coroutineScope.launch {
//                            viewModel.updateWorkout()
//                            showDailyDialog.value = false
//                        }
//                    }
//                )
//            }
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

@Composable
fun DailyDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    workoutFormUiState: WorkoutFormUiState,
    onValueChange: (Workout) -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        text = {
            DailyForm(
                workoutDetails = workoutFormUiState.workout,
                onValueChange = onValueChange,
            )
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
            B4LButton(
                onClick = onConfirm,
                text = "Save"
            )
        },
        modifier = modifier
    )
}

@Composable
fun DailyForm(
    workoutDetails: Workout,
    onValueChange: (Workout) -> Unit,
) {
    Column(
    ) {
        Row(
            modifier = Modifier.width(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Monday")
            Checkbox(
                checked = workoutDetails.monday,
                onCheckedChange = { onValueChange(workoutDetails.copy(monday = it)) }
            )
        }
        Row(
            modifier = Modifier.width(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tuesday")
            Checkbox(
                checked = workoutDetails.tuesday,
                onCheckedChange = { onValueChange(workoutDetails.copy(tuesday = it)) }
            )
        }
        Row(
            modifier = Modifier.width(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Wednesday")
            Checkbox(
                checked = workoutDetails.wednesday,
                onCheckedChange = { onValueChange(workoutDetails.copy(wednesday = it)) }
            )
        }
        Row(
            modifier = Modifier.width(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Thursday")
            Checkbox(
                checked = workoutDetails.thursday,
                onCheckedChange = { onValueChange(workoutDetails.copy(thursday = it)) }
            )
        }

        Row(
            modifier = Modifier.width(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Friday")
            Checkbox(
                checked = workoutDetails.friday,
                onCheckedChange = { onValueChange(workoutDetails.copy(friday = it)) }
            )
        }

        Row(
            modifier = Modifier.width(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Saturday")
            Checkbox(
                checked = workoutDetails.saturday,
                onCheckedChange = { onValueChange(workoutDetails.copy(saturday = it)) }
            )
        }

        Row(
            modifier = Modifier.width(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Sunday")
            Checkbox(
                checked = workoutDetails.sunday,
                onCheckedChange = { onValueChange(workoutDetails.copy(sunday = it)) }
            )
        }
    }
}