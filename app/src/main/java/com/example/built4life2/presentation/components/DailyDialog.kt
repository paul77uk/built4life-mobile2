package com.example.built4life2.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        modifier = Modifier
    ) {
        DayCheckBox(
            text = "Monday",
            checked = workoutDetails.monday,
            onCheckedChange = { onValueChange(workoutDetails.copy(monday = it)) }
        )
        DayCheckBox(
            text = "Tuesday",
            checked = workoutDetails.tuesday,
            onCheckedChange = { onValueChange(workoutDetails.copy(tuesday = it)) }
        )
        DayCheckBox(
            text = "Wednesday",
            checked = workoutDetails.wednesday,
            onCheckedChange = { onValueChange(workoutDetails.copy(wednesday = it)) }
        )
        DayCheckBox(
            text = "Thursday",
            checked = workoutDetails.thursday,
            onCheckedChange = { onValueChange(workoutDetails.copy(thursday = it)) }
        )
        DayCheckBox(
            text = "Friday",
            checked = workoutDetails.friday,
            onCheckedChange = { onValueChange(workoutDetails.copy(friday = it)) }
        )
        DayCheckBox(
            text = "Saturday",
            checked = workoutDetails.saturday,
            onCheckedChange = { onValueChange(workoutDetails.copy(saturday = it)) }
        )
        DayCheckBox(
            text = "Sunday",
            checked = workoutDetails.sunday,
            onCheckedChange = { onValueChange(workoutDetails.copy(sunday = it)) }
        )
    }
}

@Composable
fun DayCheckBox(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.width(120.dp)
    ) {
        Text(text)
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}