package com.example.built4life2.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.built4life2.R
import com.example.built4life2.data.Workout
import com.example.built4life2.presentation.workout.WorkoutFormUiState

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
                    .verticalScroll(rememberScrollState())
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

@Composable
fun WorkoutFormInput(
    workoutDetails: Workout,
    modifier: Modifier = Modifier,
    onValueChange: (Workout) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = workoutDetails.title,
            onValueChange = { onValueChange(workoutDetails.copy(title = it)) },
            label = { Text(stringResource(R.string.workout_title)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = workoutDetails.description,
            onValueChange = { onValueChange(workoutDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.workout_description)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = workoutDetails.beginner,
            onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(beginner = it)) },
            label = { Text(stringResource(R.string.beginner_level)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword
            ),
        )
        OutlinedTextField(
            value = workoutDetails.novice,
            onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(novice = it)) },
            label = { Text(stringResource(R.string.novice_level)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword
            ),
        )
        OutlinedTextField(
            value = workoutDetails.intermediate,
            onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(intermediate = it)) },
            label = { Text(stringResource(R.string.intermediate_level)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword
            ),
        )
        OutlinedTextField(
            value = workoutDetails.advanced,
            onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(advanced = it)) },
            label = { Text(stringResource(R.string.advanced_level)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword
            ),
        )
        OutlinedTextField(
            value = workoutDetails.elite,
            onValueChange = { if (it.isDigitsOnly()) onValueChange(workoutDetails.copy(elite = it)) },
            label = { Text(stringResource(R.string.elite_level)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.NumberPassword
            ),
        )
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