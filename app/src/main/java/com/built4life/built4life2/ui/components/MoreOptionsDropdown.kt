package com.built4life.built4life2.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.built4life.built4life2.R

@Composable
fun MoreOptionsDropdown(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit = {},
    onInfoClick: () -> Unit,
    onPrTypeClick: () -> Unit,
    onLevelClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onDailyClick: () -> Unit,
    isFavorite: Boolean = false,
    isDelete: Boolean = false,
    isReps: Boolean = false,
//    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    // Placeholder list of 100 strings for demonstration
    data class MenuItem(val title: String, val icon: Int)

    val commonMenuItems = listOf(
        MenuItem("Edit", R.drawable.edit),
        MenuItem("Description", R.drawable.info),
        MenuItem("PR Type", R.drawable.trophy),
        MenuItem(
            "Favorite",
            if (isFavorite) R.drawable.favorite else R.drawable.outline_favorite
        ),
        MenuItem("Daily", R.drawable.date_range)
    )

    val menuItemData = if (isDelete && isReps) {
        // Create a new list with "Delete" item at the desired position
        // (e.g., after "Edit")
        commonMenuItems.toMutableList().apply {
            add(1, MenuItem("Delete", R.drawable.delete)) // Assuming you want it after "Edit"
            add(4, MenuItem("Strength Level", R.drawable.dumbbell))
        }.toList() // Convert back to an immutable list
    } else if (isReps) {
        commonMenuItems.toMutableList().apply {
            add(3, MenuItem("Strength Level", R.drawable.dumbbell))
        }
    } else if (isDelete) {
        commonMenuItems.toMutableList().apply {
            add(1, MenuItem("Delete", R.drawable.delete)) // Assuming you want it after "Edit"
        }
    } else {
        commonMenuItems
    }

    Box(

    ) {
        IconButton(
            onClick = { expanded = !expanded },
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItemData.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.title) },
                    leadingIcon = {
                        Icon(
                            painterResource(option.icon),
                            contentDescription = option.title
                        )
                    },
                    onClick = {
                        when (option.title) {
                            "Edit" -> onEditClick()
                            "Delete" -> onDeleteClick()
                            "Description" -> onInfoClick()
                            "PR Type" -> onPrTypeClick()
                            "Strength Level" -> onLevelClick()
                            "Favorite" -> onFavoriteClick()
                            "Daily" -> onDailyClick()
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}