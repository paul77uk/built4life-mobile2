package com.example.built4life2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Workout::class], version = 1)
abstract class Built4LifeDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var Instance: Built4LifeDatabase? = null
        fun getDatabase(context: Context): Built4LifeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, Built4LifeDatabase::class.java, "built4life_database")
//                    .createFromAsset("database/built4life_database.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}