package com.built4life.built4life2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.built4life.built4life2.R
import com.built4life.built4life2.data.entity.Workout
import com.built4life.built4life2.ui.screen.workout.WorkoutFormUiState

@Composable
fun WorkoutFormDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit,
    workoutFormUiState: WorkoutFormUiState,
    onValueChange: (Workout) -> Unit,
    isEdit: Boolean = false,
) {
    AlertDialog(
        title = {
            Text(
                if (isEdit) stringResource(R.string.edit_workout) else
                    stringResource(R.string.new_workout),
            )
        },
        text = {
            WorkoutFormInput(
                workoutDetails = workoutFormUiState.workout,
                onValueChange = onValueChange,
                modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .verticalScroll(rememberScrollState()),
            )
        },
        dismissButton = {
            B4LButton(
                onClick = onDismiss,
                text = "Cancel",
                type = ButtonType.OUTLINE
            )
        },
        confirmButton = {
            B4LButton(
                onClick = onSaveClick,
                text = "Save",
                enabled = workoutFormUiState.isEntryValid,
            )
        },
        onDismissRequest = onDismiss,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutFormInput(
    workoutDetails: Workout,
    modifier: Modifier = Modifier,
    onValueChange: (Workout) -> Unit,
) {
    val options = listOf(
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
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var isSelected by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = workoutDetails.title,
            onValueChange = { onValueChange(workoutDetails.copy(title = it)) },
            label = { Text(stringResource(R.string.workout_title)) },
            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth(),
                readOnly = true,
                value = if (workoutDetails.category != "") workoutDetails.category else if (isSelected) selectedOptionText else "Category",
                onValueChange = { },
//                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            onValueChange(workoutDetails.copy(category = selectedOptionText))
                            expanded = false
                            isSelected = true
                        },
                    )
                }
            }
        }
        OutlinedTextField(
            value = workoutDetails.description,
            onValueChange = { onValueChange(workoutDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.workout_description)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
        )

//        if (workoutDetails.prType == "Reps") {
//
//            OutlinedTextField(
//                value = workoutDetails.beginner,
//                onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(beginner = it)) },
//                label = { Text(stringResource(R.string.beginner_level)) },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next,
//                    keyboardType = KeyboardType.NumberPassword
//                ),
//            )
//            OutlinedTextField(
//                value = workoutDetails.novice,
//                onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(novice = it)) },
//                label = { Text(stringResource(R.string.novice_level)) },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next,
//                    keyboardType = KeyboardType.NumberPassword
//                ),
//            )
//            OutlinedTextField(
//                value = workoutDetails.intermediate,
//                onValueChange = {
//                    if (it.isDigitsOnly()) onValueChange(
//                        workoutDetails.copy(
//                            intermediate = it
//                        )
//                    )
//                },
//                label = { Text(stringResource(R.string.intermediate_level)) },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next,
//                    keyboardType = KeyboardType.NumberPassword
//                ),
//            )
//            OutlinedTextField(
//                value = workoutDetails.advanced,
//                onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(advanced = it)) },
//                label = { Text(stringResource(R.string.advanced_level)) },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next,
//                    keyboardType = KeyboardType.NumberPassword
//                ),
//            )
//            OutlinedTextField(
//                value = workoutDetails.elite,
//                onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(elite = it)) },
//                label = { Text(stringResource(R.string.elite_level)) },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done,
//                    keyboardType = KeyboardType.NumberPassword
//                ),
//            )
//
//        }
//        OutlinedTextField(
//            value = workoutDetails.notes,
//            onValueChange = { onValueChange(workoutDetails.copy(notes = it)) },
//            label = { Text(stringResource(R.string.notes)) },
//            modifier = Modifier.fillMaxWidth(),
//            minLines = 3,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//        )

    }
}

