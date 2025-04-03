package com.example.built4life2.customcomposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.built4life2.data.Workout
import com.example.built4life2.designsystem.component.button.B4LButton
import com.example.built4life2.designsystem.component.button.ButtonType

@Composable
fun PRDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    workoutDetails: Workout,
    onValueChange: (Workout) -> Unit,
    onClick: () -> Unit
) {
    AlertDialog(
        title = { Text(text = "Edit PR") },
        text = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                ) {
                    Text("Enter a new PR for this workout.")
                }
                OutlinedTextField(
                    label = { Text("Reps") },
                    value = workoutDetails.reps,
                    onValueChange = { onValueChange(workoutDetails.copy(reps = it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text("Distance") },
                    value = workoutDetails.distance,
                    onValueChange = { onValueChange(workoutDetails.copy(distance = it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text("Weight") },
                    value = workoutDetails.weight,
                    onValueChange = { onValueChange(workoutDetails.copy(weight = it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        onDismissRequest = onDismissRequest,
        dismissButton = {
            B4LButton(
                onClick = onDismissRequest,
                text = "Cancel",
                type = ButtonType.OUTLINE
            )
        },
        confirmButton = {
            B4LButton(
                onClick = {
                    onClick()
//                    onDismissRequest()
                },
                text = "Save"
            )
        },
        shape = RoundedCornerShape(size = 10.dp),
        modifier = modifier,
    )
}