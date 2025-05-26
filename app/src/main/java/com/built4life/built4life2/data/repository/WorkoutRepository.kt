package com.built4life.built4life2.data.repository

import com.built4life.built4life2.data.dao.WorkoutDao
import com.built4life.built4life2.data.entity.Workout
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class WorkoutRepository @Inject constructor(private val workoutDao: WorkoutDao) {

    val getAllWorkouts: Flow<List<Workout>> = workoutDao.getAllWorkouts()
    val getFavoriteWorkouts: Flow<List<Workout>> = workoutDao.getFavoriteWorkouts()

    val getMondayWorkouts: Flow<List<Workout>> = workoutDao.getMondayWorkouts()
    val getTuesdayWorkouts: Flow<List<Workout>> = workoutDao.getTuesdayWorkouts()
    val getWednesdayWorkouts: Flow<List<Workout>> = workoutDao.getWednesdayWorkouts()
    val getThursdayWorkouts: Flow<List<Workout>> = workoutDao.getThursdayWorkouts()
    val getFridayWorkouts: Flow<List<Workout>> = workoutDao.getFridayWorkouts()
    val getSaturdayWorkouts: Flow<List<Workout>> = workoutDao.getSaturdayWorkouts()
    val getSundayWorkouts: Flow<List<Workout>> = workoutDao.getSundayWorkouts()

    fun getWorkoutById(id: Int): Flow<Workout> = workoutDao.getWorkout(id)

    suspend fun insertWorkout(workout: Workout) = workoutDao.insert(workout)
    suspend fun updateWorkout(workout: Workout) = workoutDao.update(workout)
    suspend fun deleteWorkout(workout: Workout) = workoutDao.delete(workout)

    fun searchWorkoutsByTitle(searchQuery: String): Flow<List<Workout>> =
        workoutDao.searchWorkoutsByTitle("%${searchQuery}%")

    fun searchFavoriteWorkoutsByTitle(searchQuery: String): Flow<List<Workout>> =
        workoutDao.searchFavoriteWorkoutsByTitle("%${searchQuery}%")

    fun getWorkoutsByCategory(category: String): Flow<List<Workout>> = workoutDao.getWorkoutsByCategory(category)
}