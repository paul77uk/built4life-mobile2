package com.built4life.built4life2.ui.screen.program

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.built4life.built4life2.data.entity.Day
import com.built4life.built4life2.data.dao.DayDao
import com.built4life.built4life2.data.entity.Program
import com.built4life.built4life2.data.dao.ProgramDao
import com.built4life.built4life2.data.relation.ProgramWithDays
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgramViewModel(
    private val programDao: ProgramDao,
    private val dayDao: DayDao
) : ViewModel() {

    val programListUiState: StateFlow<ProgramListUiState> =
        programDao.getPrograms().map { ProgramListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ProgramListUiState()
            )

    val programWithDaysUiState: StateFlow<List<ProgramWithDays>> =
        programDao.getProgramsWithDays().map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )


    var programFormUiState by mutableStateOf(ProgramFormUiState())

    fun updateUiState(program: Program) {
        programFormUiState = ProgramFormUiState(
            program = program,
            isEntryValid = validateInput(program)
        )
    }

    suspend fun saveProgram() {
        val days =
            listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        if (validateInput()) {
            programDao.insert(programFormUiState.program)
            days.forEach { day ->
                dayDao.insert(
                    Day(
                        title = day,
                        programId = programFormUiState.program.programId
                    )
                )
            }
        }
    }

    suspend fun updateProgram() {
        if (validateInput(programFormUiState.program)) {
            programDao.update(programFormUiState.program)
        }
    }

    suspend fun deleteProgram() {
        programDao.delete(programFormUiState.program)
    }

    private fun validateInput(program: Program = programFormUiState.program): Boolean {
        return with(program) {
            title.isNotBlank()
        }
    }

    suspend fun getProgramsWithDays(): List<ProgramWithDays> {
        return programDao.getProgramsWithDays().first()
    }

}

data class ProgramListUiState(val programList: List<Program> = listOf())
data class ProgramFormUiState(
    var program: Program = Program(
        title = "",
    ),
    val isEntryValid: Boolean = false
)