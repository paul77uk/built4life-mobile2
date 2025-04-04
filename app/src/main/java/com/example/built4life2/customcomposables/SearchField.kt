package com.example.built4life2.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(
    searchText: String, onSearchTextChanged: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                16.dp
            ).clip(
                shape = MaterialTheme.shapes.small
            )
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        placeholder = { Text(text = "Search workouts...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
    )
}