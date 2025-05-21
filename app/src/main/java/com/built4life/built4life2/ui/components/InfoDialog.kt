package com.built4life.built4life2.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InfoDialog(
    modifier: Modifier = Modifier,
    description: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = "Description") },
        text = {
            if (description.isEmpty()) {
                Text(text = "No description added")
            }
            Text(text = description)
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

        },
        modifier = modifier
    )
}
