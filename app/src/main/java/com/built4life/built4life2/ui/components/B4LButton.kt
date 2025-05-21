package com.built4life.built4life2.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class ButtonType {
    PRIMARY,
    OUTLINE
}

@Composable
fun B4LButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType = ButtonType.PRIMARY,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    when (type) {
        ButtonType.PRIMARY -> {
            Button(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled
            ) {
                Text(text = text)
            }
        }
        ButtonType.OUTLINE -> {
            OutlinedButton(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled
            ) {
                Text(text = text)
            }
        }
    }

}