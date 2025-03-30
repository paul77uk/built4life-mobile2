package com.example.built4life2.designsystem.component.container

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenContainer(
    showError: Boolean = false,
    errorMessage: String,
    content: @Composable () -> Unit
) {
    var showErrorMessage by remember { mutableStateOf(showError) }

    LaunchedEffect(key1 = showError) {
        showErrorMessage = showError
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(8.dp),
            visible = showErrorMessage,
            enter = slideInVertically(initialOffsetY = { -30 }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(initialAlpha = 0.3f),
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}