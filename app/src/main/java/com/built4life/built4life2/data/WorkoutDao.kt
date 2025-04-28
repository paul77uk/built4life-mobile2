package com.built4life.built4life2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * from workouts order by title")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE favorite = 1 order by favoriteOrder")
    fun getFavoriteWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE monday = 1 order by mondayOrder")
    fun getMondayWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE tuesday = 1 order by tuesdayOrder")
    fun getTuesdayWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE wednesday = 1 order by wednesdayOrder")
    fun getWednesdayWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE thursday = 1 order by thursdayOrder")
    fun getThursdayWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE friday = 1 order by fridayOrder")
    fun getFridayWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE saturday = 1 order by saturdayOrder")
    fun getSaturdayWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE sunday = 1 order by sundayOrder")
    fun getSundayWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE workoutId = :id")
    fun getWorkout(id: Int): Flow<Workout>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Workout)

    @Update
    suspend fun update(item: Workout)

    @Delete
    suspend fun delete(item: Workout)

}