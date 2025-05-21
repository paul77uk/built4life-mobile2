package com.built4life.built4life2.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.built4life.built4life2.data.entity.Day
import com.built4life.built4life2.data.entity.Program

data class ProgramWithDays(
    @Embedded val program: Program,
    @Relation(
        parentColumn = "programId",
        entityColumn = "programId"
    )
    val days: List<Day>
)