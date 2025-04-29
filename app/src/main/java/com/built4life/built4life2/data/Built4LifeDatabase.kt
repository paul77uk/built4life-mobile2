package com.built4life.built4life2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Workout::class, Program::class, Day::class], version = 1)
abstract class Built4LifeDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun programDao(): ProgramDao
    abstract fun dayDao(): DayDao

    companion object {
        private const val DATABASE_NAME = "built4life_database"
        private const val DATABASE_ASSET_PATH = "database/built4life_database.db"

        @Volatile
        private var instance: Built4LifeDatabase? = null

        fun getInstance(context: Context): Built4LifeDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): Built4LifeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                Built4LifeDatabase::class.java,
                DATABASE_NAME
            ).createFromAsset(DATABASE_ASSET_PATH)
                .build()
        }
    }
}