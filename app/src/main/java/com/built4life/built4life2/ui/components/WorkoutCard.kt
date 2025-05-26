package com.built4life.built4life2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.built4life.built4life2.R
import com.built4life.built4life2.data.entity.Workout

@Composable
fun WorkoutCard(
    workout: Workout,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPrClick: () -> Unit,
    onInfoClick: () -> Unit,
    onPrTypeClick: () -> Unit,
    onLevelClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onDailyClick: () -> Unit,
    isDelete: Boolean = true,
    isReps: Boolean = false
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
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                BasicDropdownMenu(
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    onInfoClick = onInfoClick,
                    onFavoriteClick = onFavoriteClick,
                    onPrTypeClick = onPrTypeClick,
                    onLevelClick = onLevelClick,
                    isFavorite = workout.favorite,
                    onDailyClick = onDailyClick,
                    isDelete = isDelete,
                    isReps = isReps
                )
            }
            PRComposable(
                workout = workout,
                onPrClick = onPrClick
            )


        }
    }
}

@Composable
fun BasicDropdownMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onInfoClick: () -> Unit,
    onPrTypeClick: () -> Unit,
    onLevelClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    isDelete: Boolean = true,
    isReps: Boolean = false,
    onDailyClick: () -> Unit,
    enabled: Boolean = true,
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
                    leadingIcon = {
                        Icon(
                            painterResource(option.icon),
                            contentDescription = option.title
                        )
                    },
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

                            "PR Type" -> {
                                onPrTypeClick()
                                expanded = false
                            }

                            "Strength Level" -> {
                                onLevelClick()
                                expanded = false
                            }

                            "Favorite" -> {
                                onFavoriteClick()
                                expanded = false
                            }

                            "Daily" -> {
                                onDailyClick()
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
    HorizontalDivider()
    if (workout.reps.isNotEmpty() && workout.prType == "Reps") {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 10.dp)
        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(24.dp),
//                modifier = Modifier.padding(16.dp)
//            ) {

            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(2.6f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    PRRow(
                        text1 = workout.reps,
                        text2 = "REPS ${if (workout.weight.isNotEmpty()) "X" else ""}"
                    )
                    if (workout.weight.isNotEmpty())
                        PRRow(
                            text1 = workout.weight,
                            text2 = "KG"
                        )
                }
//                    if (workout.totalReps.isNotEmpty())
//                    PRRow(
//                        text1 = workout.totalReps,
//                        text2 = "TOTAL REPS"
//                    )
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

            var weighted = true
            val max: Double
            if (workout.weight.isNotEmpty()) {
                max =
                    workout.weight.toInt() * workout.reps.toInt() * 0.0333 + workout.weight.toInt()

            } else {
                max = workout.reps.toDouble()
                weighted = false
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(2.9f)
            ) {
                if (workout.novice.isEmpty() || workout.intermediate.isEmpty() || workout.advanced.isEmpty() || workout.elite.isEmpty())
                    RepMax(
                        repMax = max.toInt().toString(),
                        level = "",
                        weighted = weighted,
                        eliteLevel = workout.elite,
                        workout = workout
                    )
                else if (workout.reps.isNotEmpty()) {
                    if (workout.prType == "Time") {
                        when (max.toInt()) {
                            in workout.elite.toInt() - 1 downTo 0 -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "ELITE",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            in workout.advanced.toInt() - 1 downTo workout.elite.toInt() -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "ADVANCED",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            in workout.intermediate.toInt() - 1 downTo workout.advanced.toInt() -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "INTERMEDIATE",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            in workout.novice.toInt() - 1 downTo workout.intermediate.toInt() -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "NOVICE",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            else -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "BEGINNER",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                        }
                    } else {
                        when (max.toInt()) {
                            in 0..<workout.novice.toInt() -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "BEGINNER",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            in workout.novice.toInt()..<workout.intermediate.toInt() -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "NOVICE",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            in workout.intermediate.toInt()..<workout.advanced.toInt() -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "INTERMEDIATE",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            in workout.advanced.toInt()..<workout.elite.toInt() -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "ADVANCED",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }

                            else -> {
                                RepMax(
                                    repMax = max.toInt().toString(),
                                    level = "ELITE",
                                    weighted = weighted,
                                    eliteLevel = workout.elite,
                                    workout = workout
                                )
                            }
                        }
                    }
                }
            }

            IconButton(
                onClick = onPrClick,
                modifier = Modifier.width(25.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .fillMaxWidth()
                )
            }
        }
    } else if (workout.rounds.isNotEmpty() && workout.prType == "Rounds") {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 10.dp)
        ) {
            PRRow(
                text1 = workout.rounds,
                text2 = "ROUNDS"
            )
            IconButton(
                onClick = onPrClick,
                modifier = Modifier.width(25.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .fillMaxWidth()
                )
            }
        }
    } else if (workout.minutes.isNotEmpty() && workout.prType == "Time") {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 10.dp)
        ) {
            PRRow(
                text1 = "${workout.minutes}${if (workout.seconds.isNotEmpty()) ":${workout.seconds}" else ""}",
                text2 = "MIN"
            )
            IconButton(
                onClick = onPrClick,
                modifier = Modifier.width(25.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .fillMaxWidth()
                )
            }
        }
    } else if (workout.distance.isNotEmpty() && workout.prType == "Distance") {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 10.dp)
        ) {
            PRRow(
                text1 = workout.distance,
                text2 = "METRES"
            )
            IconButton(
                onClick = onPrClick,
                modifier = Modifier.width(25.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .fillMaxWidth()
                )
            }
        }
    } else if (workout.weight.isNotEmpty() && workout.prType == "Weight") {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 10.dp)
        ) {
            PRRow(
                text1 = workout.weight,
                text2 = "KG"
            )
            IconButton(
                onClick = onPrClick,
                modifier = Modifier.width(25.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .fillMaxWidth()
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
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text1,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        )
        Text(
            text = text2,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 8.sp,
        )
    }
}

@Composable
fun RepMax(
    modifier: Modifier = Modifier,
    repMax: String,
    level: String,
    eliteLevel: String,
    weighted: Boolean = true,
    workout: Workout
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (weighted)
            Text(
                "1 REP MAX: $repMax KG",
                fontWeight = FontWeight.SemiBold,
                fontSize = 8.sp,
                color = Color.Gray,
            )
        if (level.isNotEmpty())
            StrengthLevelCard(
                repMax = repMax.toInt(),
                eliteLevel = eliteLevel.toInt(),
                beginnerLevel = if (workout.prType == "Time") workout.beginner.toInt() else 0
            )
        Text(
            level,
            fontWeight = FontWeight.SemiBold,
            fontSize = 8.sp,
            color = Color.Gray,
        )
    }
}