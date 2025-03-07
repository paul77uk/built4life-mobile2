package com.example.built4life2.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.built4life2.R
import com.example.built4life2.data.Workout
import com.example.built4life2.ui.ViewModelProvider
import com.example.built4life2.ui.navigation.NavigationDestination
import com.example.built4life2.ui.viewmodels.WorkoutFormUiState
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
        Column {
            WorkoutList(
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged,
                workoutList = workoutListState.workoutList,
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
                onEditClick = {
                    openDialog.value = true
                    isEdit.value = true
                },
                onDeleteClick = {
                    showDeleteConfirmation.value = true
                }
            )
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
                    viewModel = viewModel,
                    isEdit = isEdit.value
                )
            }
            if (showDeleteConfirmation.value) {
                BasicAlertDialog(
                    onDismissRequest = { showDeleteConfirmation.value = false },
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
                                onClick = { showDeleteConfirmation.value = false },
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
                                        openDialog.value = false
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


@Composable
fun WorkoutList(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    workoutList: List<Workout>,
    viewModel: WorkoutViewModel,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = onSearchTextChanged
        )
        Spacer(modifier = Modifier.padding(16.dp))
        if (workoutList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_workouts_added),
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            LazyColumn {
                items(items = workoutList, key = { it.id }) { workout ->
                    SingleWorkout(
                        workout = workout,
                        workoutFormUiState = WorkoutFormUiState(),
                        viewModel = viewModel,
                        onEditClick = onEditClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        placeholder = { Text(text = "Search workouts...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
    )
}

@Composable
fun SingleWorkout(
    workout: Workout,
    workoutFormUiState: WorkoutFormUiState,
    viewModel: WorkoutViewModel,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(5),
//        shadowElevation = 4.dp,
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
    ) {
        Column {
            ListItem(
                headlineContent = {
                    Text(
                        workout.title,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                supportingContent = {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            workout.description,
                            color = Color.Gray,
                        )

                        Text(workout.workoutDetails, Modifier.padding(8.dp))

                        if (workout.pr.isNotEmpty())
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),

                                ) {
                                Text(
                                    "PR: ",
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    workout.pr,
                                    Modifier
                                        .border(
                                            width = 1.dp,
                                            color = Color.LightGray,
                                            shape = RoundedCornerShape(15),
                                        )
                                        .padding(
                                            horizontal = 8.dp,
                                            vertical = 4.dp,
                                        ),
                                )
                            }
                    }
                },
            )

            HorizontalDivider(
                color = Color.LightGray,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sharp_trophy_24),
                        contentDescription = "PR Icon"
                    )
                }
                IconButton(
                    onClick = {},
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = "PR Icon"
                    )
                }
                IconButton(
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sharp_notes_24),
                        contentDescription = "Notes Icon"
                    )
                }
                IconButton(
                    onClick = {
                        onEditClick()
                        coroutineScope.launch {
                            workoutFormUiState.workout = workout
                            viewModel.updateUiState(workout)
                            viewModel.updateWorkout()
                        }
                    },
                ) {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Edit Icon"
                    )
                }
                IconButton(
                    onClick = {
                        onDeleteClick()
                        workoutFormUiState.workout = workout
                        viewModel.updateUiState(workout)
                    },
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "Delete Icon"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutFormDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit,
//    onDeleteClick: () -> Unit,
    workoutFormUiState: WorkoutFormUiState,
    viewModel: WorkoutViewModel,
    isEdit: Boolean = false
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5))
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = modifier.padding(vertical = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            if (isEdit) stringResource(R.string.edit_workout) else
                                stringResource(R.string.new_workout),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.clickable {

                    }
                )
                WorkoutFormInput(
                    workoutDetails = workoutFormUiState.workout,
                    onValueChange = viewModel::updateUiState,
                    modifier = Modifier.fillMaxWidth()
                )
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .padding(top = 16.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                    ),
                    shape = RoundedCornerShape(15),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                ) {
                    Text(stringResource(R.string.cancel))
                }
//            if (isEdit) {
//                Button(
//                    onClick = onDeleteClick,
//                    shape = RoundedCornerShape(15),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Red,
//                    ),
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .fillMaxWidth(),
//                ) {
//                    Text(stringResource(R.string.delete_workout))
//                }
//            }
                Button(
                    onClick = onSaveClick,
                    shape = RoundedCornerShape(15),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    enabled = workoutFormUiState.isEntryValid,
                ) {
                    Text(stringResource(R.string.save_workout))
                }
//            }
            }
        }
    }
}

@Composable
fun WorkoutFormInput(
    workoutDetails: Workout,
    modifier: Modifier = Modifier,
    onValueChange: (Workout) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = workoutDetails.title,
            onValueChange = { onValueChange(workoutDetails.copy(title = it)) },
            label = { Text(stringResource(R.string.workout_title)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = workoutDetails.description,
            onValueChange = { onValueChange(workoutDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.workout_description)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = workoutDetails.workoutDetails,
            onValueChange = { onValueChange(workoutDetails.copy(workoutDetails = it)) },
            label = { Text(stringResource(R.string.workout_details)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
        )
        OutlinedTextField(
            value = workoutDetails.pr,
            onValueChange = { onValueChange(workoutDetails.copy(pr = it)) },
            label = { Text(stringResource(R.string.pr)) },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = workoutDetails.info,
            onValueChange = { onValueChange(workoutDetails.copy(info = it)) },
            label = { Text(stringResource(R.string.info)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
        )
        OutlinedTextField(
            value = workoutDetails.notes,
            onValueChange = { onValueChange(workoutDetails.copy(notes = it)) },
            label = { Text(stringResource(R.string.notes)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
        )

    }
}