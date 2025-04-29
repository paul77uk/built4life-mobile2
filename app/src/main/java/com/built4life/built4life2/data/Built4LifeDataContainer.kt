package com.built4life.built4life2.data

import android.content.Context

class Built4LifeDataContainer(private val context: Context) {

    private val database: Built4LifeDatabase by lazy {
        Built4LifeDatabase.getInstance(context)
    }

    val workout: WorkoutDao by lazy {
        database.workoutDao()
    }

    val program: ProgramDao by lazy {
        database.programDao()
    }

    val day: DayDao by lazy {
        database.dayDao()
    }
}