package com.example.built4life2.customcomposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.built4life2.R
import com.example.built4life2.data.Workout
import com.example.built4life2.designsystem.component.button.B4LButton
import com.example.built4life2.designsystem.component.button.ButtonType
import com.example.built4life2.ui.viewmodels.WorkoutFormUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutFormDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit,
    workoutFormUiState: WorkoutFormUiState,
    onValueChange: (Workout) -> Unit,
    isEdit: Boolean = false
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
        )
        OutlinedTextField(
            value = workoutDetails.description,
            onValueChange = { onValueChange(workoutDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.workout_description)) },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = workoutDetails.workoutDetails,
            onValueChange = { onValueChange(workoutDetails.copy(workoutDetails = it)) },
            label = { Text(stringResource(R.string.workout_details)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
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