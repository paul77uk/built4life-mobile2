package com.example.built4life2.customcomposables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.built4life2.data.Workout
import com.example.built4life2.designsystem.component.button.B4LButton
import com.example.built4life2.designsystem.component.button.ButtonType

@Composable
fun WorkoutCard(
    workout: Workout,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPrClick: () -> Unit,
    onInfoClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(5),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    workout.title.uppercase(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                BasicDropdownMenu(
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    onInfoClick = onInfoClick,
                )
//                Row(
//                    modifier = Modifier.weight(2f)
//                ){
//                    IconButton(
//                        onClick = onEditClick,
//                        modifier = Modifier.border(
//                            width = 1.dp,
//                            color = Color.LightGray,
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                    ) {
//                        Icon(
//                            Icons.Outlined.Edit,
//                            contentDescription = "Edit Icon"
//                        )
//                    }
//                    IconButton(
//                        onClick = onDeleteClick,
//                        modifier = Modifier.border(
//                            width = 1.dp,
//                            color = Color.LightGray,
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                    ) {
//                        Icon(
//                            Icons.Outlined.Delete,
//                            contentDescription = "Delete Icon"
//                        )
//                    }
//                }
            }


//                supportingContent = {
//
//                    Column(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            workout.description,
//                            color = Color.Gray,
//                        )
//
//                        Text(workout.workoutDetails, Modifier.padding(8.dp))
//
//                    }
//                },


//            if (workout.description.isNotEmpty()) {
//                HorizontalDivider()
//                Text(
//                    workout.description,
//                    modifier = Modifier
//                        .padding(22.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//            }

            PRComposable(
                workout = workout,
                onPrClick = onPrClick
            )

//            HorizontalDivider(
//                color = Color.LightGray,
//            )
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                IconButton(
//                    onClick = onInfoClick,
//                ) {
//                    Icon(
//                        Icons.Outlined.Info,
//                        contentDescription = "PR Icon"
//                    )
//                }
//                IconButton(
//                    onClick = onNotesClick,
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.sharp_notes_24),
//                        contentDescription = "Notes Icon"
//                    )
//                }
//                IconButton(
//                    onClick = onEditClick
//
//                ) {
//                    Icon(
//                        Icons.Outlined.Edit,
//                        contentDescription = "Edit Icon"
//                    )
//                }
//                IconButton(
//                    onClick = onDeleteClick
//                ) {
//                    Icon(
//                        Icons.Outlined.Delete,
//                        contentDescription = "Delete Icon"
//                    )
//                }
//            }
        }
    }
}

@Composable
fun BasicDropdownMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    // Placeholder list of 100 strings for demonstration
    data class MenuItem(val title: String, val icon: ImageVector)

    val menuItemData = listOf(
        MenuItem("Edit", Icons.Outlined.Edit),
        MenuItem("Delete", Icons.Outlined.Delete),
        MenuItem("Description", Icons.Outlined.Info),
    )

    Box(
        modifier = modifier
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
                    leadingIcon = { Icon(option.icon, contentDescription = null) },
                    onClick = {
                        when (option.title) {
                            "Edit" -> {
                                onEditClick()
                                expanded = false
                            }

                            "Delete" -> {
                                onDeleteClick()
                                expanded = false
                            }

                            "Description" -> {
                                onInfoClick()
                                expanded = false
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PRComposable(
    workout: Workout,
    onPrClick: () -> Unit
) {
    if (workout.totalReps.isNotEmpty() && workout.firstSetReps.isNotEmpty()) {
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                if (workout.totalReps.isNotEmpty() && workout.firstSetReps.isNotEmpty()) {
                    Column {
                        PRRow(
                            text1 = workout.firstSetReps,
                            text2 = "UNBROKEN REPS"
                        )
                        PRRow(
                            text1 = workout.totalReps,
                            text2 = "TOTAL REPS"
                        )
                    }
                }
//                if (workout.distance.isNotEmpty()) {
//                    Text(
//                        text = "${workout.distance} METRES",
//                    )
//                }
//                if (workout.weight.isNotEmpty()) {
//                    Text(
//                        text = "@${workout.weight} KG",
//                    )
//                }
            }
            var weighted = true
            val max: Double
            if (workout.weight.isNotEmpty()) {
                max =
                    workout.weight.toInt() * workout.firstSetReps.toInt() * 0.0333 + workout.weight.toInt()

            } else {
                max = workout.firstSetReps.toDouble()
                weighted = false
            }

            if (workout.novice.isEmpty() || workout.intermediate.isEmpty() || workout.advanced.isEmpty() || workout.elite.isEmpty())
                RepMax(
                    repMax = workout.totalReps,
                    level = "",
                    weighted = weighted
                )
            else if (workout.totalReps.isNotEmpty()) {
                when (max.toInt()) {
                    in 0..<workout.novice.toInt() -> {
                        RepMax(
                            repMax = max.toInt().toString(),
                            level = "BEGINNER",
                            weighted = weighted
                        )
                    }

                    in workout.novice.toInt()..<workout.intermediate.toInt() -> {
                        RepMax(
                            repMax = max.toInt().toString(),
                            level = "NOVICE",
                            weighted = weighted
                        )
                    }

                    in workout.intermediate.toInt()..<workout.advanced.toInt() -> {
                        RepMax(
                            repMax = max.toInt().toString(),
                            level = "INTERMEDIATE",
                            weighted = weighted
                        )
                    }

                    in workout.advanced.toInt()..<workout.elite.toInt() -> {
                        RepMax(
                            repMax = max.toInt().toString(),
                            level = "ADVANCED",
                            weighted = weighted
                        )
                    }

                    else -> {
                        RepMax(
                            repMax = max.toInt().toString(),
                            level = "ELITE",
                            weighted = weighted
                        )
                    }
                }
            }

            IconButton(
                onClick = onPrClick,
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Icon"
                )
            }
        }
    } else {
        B4LButton(
            onClick = onPrClick,
            text = "LOG SCORE",
            type = ButtonType.OUTLINE,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }

}

@Composable
fun PRRow(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text1,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
        Text(
            text = text2,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
        )
    }
}

@Composable
fun RepMax(
    modifier: Modifier = Modifier,
    repMax: String,
    level: String,
    weighted: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (weighted)
            Text(
                "1 REP MAX: $repMax KG",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color.Gray,
            )
        if (level.isNotEmpty())
            Text(
                level,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color.Gray,
            )
    }
}