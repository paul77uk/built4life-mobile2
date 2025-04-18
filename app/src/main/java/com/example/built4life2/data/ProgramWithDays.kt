package com.example.built4life2.data

import androidx.room.Embedded
import androidx.room.Relation

data class ProgramWithDays(
    @Embedded val program: Program,
    @Relation(
        parentColumn = "programId",
        entityColumn = "programId"
    )
    val days: List<Day>
)
