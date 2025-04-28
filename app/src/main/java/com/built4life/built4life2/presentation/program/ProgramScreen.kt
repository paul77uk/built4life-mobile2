package com.built4life.built4life2.presentation.program

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.built4life.built4life2.data.Program
import com.built4life.built4life2.presentation.components.B4LButton
import com.built4life.built4life2.presentation.components.ButtonType
import com.built4life.built4life2.presentation.ViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun ProgramScreen(viewModel: ProgramViewModel = viewModel(factory = ViewModelProvider.Factory)) {

    val programListState by viewModel.programListUiState.collectAsState()
    val programWithDaysState by viewModel.programWithDaysUiState.collectAsState()
    val programFormUiState = viewModel.programFormUiState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    var programState by remember { mutableStateOf(Program(title = "")) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row {
                    Text("Programs", modifier = Modifier.padding(16.dp))
                    IconButton(
                        onClick = {
                            openDialog.value = true
                        }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add program")
                    }
                }
                HorizontalDivider()
                programListState.programList.forEach { program ->
                    NavigationDrawerItem(
                        label = { Text(text = program.title) },
                        selected = false,
                        onClick = {
                            programState = program
                            scope.launch { drawerState.close() }
                        }
                    )
                }
//                NavigationDrawerItem(
//                    label = { Text(text = "Drawer Item") },
//                    selected = false,
//                    onClick = { /*TODO*/ }
//                )
                // ...other drawer items
            }
        },
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show drawer") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->
            // Screen content
            IconButton(
                onClick = {

                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Day")
            }
            programWithDaysState.forEach { programWithDays ->
                if (programWithDays.program.programId == programState.programId) {
                    programWithDays.days.forEach { day ->
                        Text(day.title, modifier = Modifier.padding(contentPadding))
                    }
                }
            }
            if (openDialog.value) {
                ProgramDialog(
                    onDismiss = {
                        scope.launch {
                            viewModel.updateUiState(
                                Program(
                                    title = "",
                                )
                            )
                            openDialog.value = false
                        }
                    },
                    onSaveClick = {
                        scope.launch {
                            viewModel.saveProgram()
                            viewModel.updateUiState(
                                Program(
                                    title = "",
                                )
                            )
                            openDialog.value = false
                        }
                    },
                    programFormUiState = programFormUiState,
                    onValueChange = viewModel::updateUiState,
                    isEdit = false
                )
            }
        }
    }
}

@Composable
fun ProgramDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit,
    programFormUiState: ProgramFormUiState,
    onValueChange: (Program) -> Unit,
    isEdit: Boolean = false,
) {
    AlertDialog(
        title = {
            Text(
                if (isEdit) "Edit Program" else
                    "Add Program",
            )
        },
        text = {
            OutlinedTextField(
                value = programFormUiState.program.title,
                onValueChange = { onValueChange(programFormUiState.program.copy(title = it)) },
                label = { Text("Program Name") },
            )
        },
        dismissButton = {
            B4LButton(
                onClick = onDismiss,
                text = "Cancel",
                type = ButtonType.OUTLINE
            )
        },
        confirmButton = {
            B4LButton(
                onClick = onSaveClick,
                text = "Save",
                enabled = programFormUiState.isEntryValid,
            )
        },
        onDismissRequest = onDismiss,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5))
    )
}