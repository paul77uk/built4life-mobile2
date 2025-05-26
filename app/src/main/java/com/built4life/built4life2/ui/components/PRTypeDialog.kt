package com.built4life.built4life2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.built4life.built4life2.data.entity.Workout

@Composable
fun PRTypeDialog(
    modifier: Modifier = Modifier,
    workoutDetails: Workout,
    onValueChange: (Workout) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        text = {
            RadioGroupSample(
                selectedOption = workoutDetails.prType,
                onOptionSelected = { onValueChange(workoutDetails.copy(prType = it)) }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RadioGroupSample(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val radioOptions = listOf("Reps", "Rounds", "Time", "Distance", "Weight")
    Column {
        Text(
            text = "PR Type",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
//            fontWeight = FontWeight.Bold
        )
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        FlowRow(
            Modifier.selectableGroup(),
            verticalArrangement = Arrangement.Center,
//            horizontalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 4
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
                        .weight(1f)
                        .padding(bottom = 16.dp),
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
}