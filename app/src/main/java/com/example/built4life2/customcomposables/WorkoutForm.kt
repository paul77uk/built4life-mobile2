package com.example.built4life2.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.built4life2.R
import com.example.built4life2.data.Workout
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
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5))
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = modifier.padding(vertical = 16.dp),
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
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth()
                )
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