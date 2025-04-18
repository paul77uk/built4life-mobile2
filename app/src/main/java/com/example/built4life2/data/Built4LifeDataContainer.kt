package com.example.built4life2.data

import android.content.Context

class Built4LifeDataContainer(private val context: Context) {
    val workoutDao: WorkoutDao by lazy {
        Built4LifeDatabase.getDatabase(context).workoutDao()
    }
    val programDao: ProgramDao by lazy {
        Built4LifeDatabase.getDatabase(context).programDao()
    }

    val dayDao: DayDao by lazy {
        Built4LifeDatabase.getDatabase(context).dayDao()
    }
}