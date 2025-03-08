package com.example.built4life2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.built4life2.R
import com.example.built4life2.customcomposables.SearchField
import com.example.built4life2.customcomposables.WorkoutCard
import com.example.built4life2.customcomposables.WorkoutFormDialog
import com.example.built4life2.data.Workout
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
    val isEdit = remember { mutableStateOf(false) }
    val showDeleteConfirmation = remember { mutableStateOf(false) }
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
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(16.dp)
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

                        },
                        onInfoClick = {},
                        onNotesClick = {}
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
                                    pr = "",
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
                                        pr = "",
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
                                        pr = "",
                                        info = "",
                                        notes = ""
                                    )
                                )
                                openDialog.value = false
                                isEdit.value = false
                            }
                    },
//                    onDeleteClick = {
//                        showDeleteConfirmation.value = true
//                    },
                    workoutFormUiState = workoutFormUiState,
                    onValueChange = viewModel::updateUiState,
                    isEdit = isEdit.value
                )
            }
            if (showDeleteConfirmation.value) {
                BasicAlertDialog(
                    onDismissRequest = {
                        showDeleteConfirmation.value = false
                        isEdit.value = false
                    },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(5))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = "Are you sure you want to delete this workout?")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.updateUiState(
                                        Workout(
                                            title = "",
                                            description = "",
                                            workoutDetails = "",
                                            pr = "",
                                            info = "",
                                            notes = ""
                                        )
                                    )
                                    showDeleteConfirmation.value = false
                                    isEdit.value = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(15),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Cancel")
                            }
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.deleteWorkout()
                                        viewModel.updateUiState(
                                            Workout(
                                                title = "",
                                                description = "",
                                                workoutDetails = "",
                                                pr = "",
                                                info = "",
                                                notes = ""
                                            )
                                        )
                                        isEdit.value = false
                                        showDeleteConfirmation.value = false
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                ),
                                shape = RoundedCornerShape(15),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Delete")
                            }
                        }
                    }
                }

            }
        }
    }
}