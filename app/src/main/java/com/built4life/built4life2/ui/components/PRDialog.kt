package com.built4life.built4life2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.built4life.built4life2.data.entity.Workout

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
                when (workoutDetails.prType) {
                    "Reps" -> {

                        OutlinedTextField(
                            label = { Text("Reps") },
                            value = workoutDetails.reps,
                            onValueChange = {
                                if (it.isDigitsOnly()) onValueChange(
                                    workoutDetails.copy(
                                        reps = it
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier.fillMaxWidth()

                        )

                        OutlinedTextField(
                            label = { Text("Weight") },
                            value = workoutDetails.weight,
                            onValueChange = {
                                if (it.isDigitsOnly()) onValueChange(
                                    workoutDetails.copy(
                                        weight = it
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    "Rounds" ->

                        OutlinedTextField(
                            label = { Text("Rounds") },
                            value = workoutDetails.rounds,
                            onValueChange = {
                                if (it.isDigitsOnly()) onValueChange(
                                    workoutDetails.copy(
                                        rounds = it
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier.fillMaxWidth()
                        )

                    "Time" ->

                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                modifier = modifier.weight(1f),
                                label = { Text("Minutes") },
                                value = workoutDetails.minutes,
                                onValueChange = {
                                    if (it.isDigitsOnly()) onValueChange(
                                        workoutDetails.copy(
                                            minutes = it
                                        )
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            )
                            Text(":")
                            OutlinedTextField(
                                modifier = modifier.weight(1f),
                                label = { Text("Seconds") },
                                value = workoutDetails.seconds,
                                onValueChange = {
                                    if (it.isDigitsOnly()) onValueChange(
                                        workoutDetails.copy(
                                            seconds = it
                                        )
                                    )

                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            )
                        }

                    "Distance" ->

                        OutlinedTextField(
                            label = { Text("Distance") },
                            value = workoutDetails.distance,
                            onValueChange = {
                                if (it.isDigitsOnly()) onValueChange(
                                    workoutDetails.copy(
                                        distance = it
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier.fillMaxWidth()
                        )


//                OutlinedTextField(
//                    label = { Text("Total Reps") },
//                    value = workoutDetails.totalReps,
//                    onValueChange = {
//                        if (it.isDigitsOnly()) onValueChange(
//                            workoutDetails.copy(
//                                totalReps = it
//                            )
//                        )
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//                    modifier = Modifier.fillMaxWidth()
//                )

                }
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
                text = "Save",
                enabled = when (workoutDetails.prType) {
                    "Time" -> workoutDetails.minutes.isNotEmpty() && (workoutDetails.seconds.isEmpty() || workoutDetails.seconds.toInt() < 60)
                    else -> workoutDetails.reps.isNotEmpty() || workoutDetails.rounds.isNotEmpty() || workoutDetails.weight.isNotEmpty() || workoutDetails.distance.isNotEmpty()
                }
            )
        },
        shape = RoundedCornerShape(size = 10.dp),
        modifier = modifier,
    )
}