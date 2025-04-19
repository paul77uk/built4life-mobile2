package com.example.built4life2.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.built4life2.data.Workout
import com.example.built4life2.presentation.workout.WorkoutFormUiState

@Composable
fun DailyDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    workoutFormUiState: WorkoutFormUiState,
    onValueChange: (Workout) -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = { Text(text = "Description") },
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
    Column {
        Row {
            Text("Monday")
            Checkbox(
                checked = workoutDetails.monday,
                onCheckedChange = { onValueChange(workoutDetails.copy(monday = it)) }
            )
        }
        Row {
            Text("Tuesday")
            Checkbox(
                checked = workoutDetails.tuesday,
                onCheckedChange = { onValueChange(workoutDetails.copy(tuesday = it)) }
            )
        }
        Row {
            Text("Wednesday")
            Checkbox(
                checked = workoutDetails.wednesday,
                onCheckedChange = { onValueChange(workoutDetails.copy(wednesday = it)) }
            )
        }
        Row {
            Text("Thursday")
            Checkbox(
                checked = workoutDetails.thursday,
                onCheckedChange = { onValueChange(workoutDetails.copy(thursday = it)) }
            )
        }

        Row {
            Text("Friday")
            Checkbox(
                checked = workoutDetails.friday,
                onCheckedChange = { onValueChange(workoutDetails.copy(friday = it)) }
            )
        }

        Row {
            Text("Saturday")
            Checkbox(
                checked = workoutDetails.saturday,
                onCheckedChange = { onValueChange(workoutDetails.copy(saturday = it)) }
            )
        }

        Row {
            Text("Sunday")
            Checkbox(
                checked = workoutDetails.sunday,
                onCheckedChange = { onValueChange(workoutDetails.copy(sunday = it)) }
            )
        }
    }
}