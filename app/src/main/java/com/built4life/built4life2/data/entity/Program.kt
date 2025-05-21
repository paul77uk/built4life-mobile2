package com.built4life.built4life2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "programs")
data class Program(
    @PrimaryKey(autoGenerate = true)
    val programId: Int = 0,
    val title: String,
)