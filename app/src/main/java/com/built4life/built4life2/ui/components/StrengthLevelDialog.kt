package com.built4life.built4life2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.built4life.built4life2.R
import com.built4life.built4life2.data.entity.Workout

@Composable
fun StrengthLevelDialog(
    modifier: Modifier = Modifier,
    workoutDetails: Workout,
    onValueChange: (Workout) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Strength Levels",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp),
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = workoutDetails.beginner,
                    onValueChange = {
                        if (it.isDigitsOnly()) onValueChange(
                            workoutDetails.copy(
                                beginner = it
                            )
                        )
                    },
                    label = { Text(stringResource(R.string.beginner_level)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.NumberPassword
                    ),
                )
                OutlinedTextField(
                    value = workoutDetails.novice,
                    onValueChange = {
                        if (it.isDigitsOnly()) onValueChange(
                            workoutDetails.copy(
                                novice = it
                            )
                        )
                    },
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
                    onValueChange = {
                        if (it.isDigitsOnly()) onValueChange(
                            workoutDetails.copy(
                                advanced = it
                            )
                        )
                    },
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
        },
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
    )
}

