package com.built4life.built4life2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
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
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = workoutDetails.description,
            onValueChange = { onValueChange(workoutDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.workout_description)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
        )
        RadioGroupSample(
            selectedOption = workoutDetails.prType,
            onOptionSelected = { onValueChange(workoutDetails.copy(prType = it)) }
        )

        if (workoutDetails.prType == "Reps") {

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
                onValueChange = {
                    if (it.isDigitsOnly()) onValueChange(
                        workoutDetails.copy(
                            intermediate = it
                        )
                    )
                },
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

        }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RadioGroupSample(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val radioOptions = listOf("Reps", "Rounds", "Time", "Distance")

    Text(
        text = "PR Type",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 8.dp),
        fontWeight = FontWeight.Bold
    )
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    FlowRow(
        Modifier.selectableGroup(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        radioOptions.forEach { text ->
            Column(
                Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                                  },
                        role = Role.RadioButton
                    )
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screenreaders
                )
            }
        }
    }
}