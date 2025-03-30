package com.example.built4life2.customcomposables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.built4life2.R
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
    onNotesClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(5),
//        shadowElevation = 4.dp,
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
    ) {
        Column {
            ListItem(
                headlineContent = {
                    Text(
                        workout.title,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                supportingContent = {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            workout.description,
                            color = Color.Gray,
                        )

                        Text(workout.workoutDetails, Modifier.padding(8.dp))

                        if (workout.pr.isNotEmpty()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                Text(
                                    "PR: ",
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    workout.pr,
                                    Modifier
                                        .border(
                                            width = 1.dp,
                                            color = Color.LightGray,
                                            shape = RoundedCornerShape(15),
                                        )
                                        .padding(
                                            horizontal = 8.dp,
                                            vertical = 4.dp,
                                        ),
                                )
                            }
                        } else {
                            B4LButton(
                                onClick = onPrClick,
                                text = "LOG SCORE",
                                type = ButtonType.OUTLINE,
                                modifier = Modifier.padding(8.dp).fillMaxWidth()
                            )
                        }
                    }
                },
            )

            HorizontalDivider(
                color = Color.LightGray,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = onPrClick,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sharp_trophy_24),
                        contentDescription = "PR Icon"
                    )
                }
                IconButton(
                    onClick = onInfoClick,
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = "PR Icon"
                    )
                }
                IconButton(
                    onClick = onNotesClick,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sharp_notes_24),
                        contentDescription = "Notes Icon"
                    )
                }
                IconButton(
                    onClick = onEditClick

                ) {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Edit Icon"
                    )
                }
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "Delete Icon"
                    )
                }
            }
        }
    }
}