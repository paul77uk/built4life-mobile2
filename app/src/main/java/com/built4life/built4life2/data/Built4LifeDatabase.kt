package com.built4life.built4life2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.built4life.built4life2.data.dao.DayDao
import com.built4life.built4life2.data.dao.ProgramDao
import com.built4life.built4life2.data.dao.WorkoutDao
import com.built4life.built4life2.data.entity.Day
import com.built4life.built4life2.data.entity.Program
import com.built4life.built4life2.data.entity.Workout

@Database(entities = [Workout::class, Program::class, Day::class], version = 1)
abstract class Built4LifeDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun programDao(): ProgramDao
    abstract fun dayDao(): DayDao

}