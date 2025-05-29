package com.built4life.built4life2.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = "Delete Workout")
            },
            text = {
                Text(text = "Are you sure you want to delete this workout?")
            },
            dismissButton = {
                B4LButton(
                    onClick = onDismissRequest,
                    text = "Cancel",
                    type = ButtonType.OUTLINE
                )
            },
            confirmButton = {
                B4LButton(
                    onClick = onConfirmClick,
                    text = "Delete"
                )
            }
        )
    }
}