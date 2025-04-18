package com.example.built4life2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Query("SELECT * from days")
    fun getAllDays(): Flow<List<Day>>

    @Query("SELECT * from days WHERE dayId = :id")
    fun getDay(id: Int): Flow<Day>

    @Query("SELECT * from days WHERE programId = :programId")
    fun getDaysForProgram(programId: Int): Flow<List<Day>>

    @Query("SELECT * from days WHERE programId = :programId AND dayId = :dayId")
    fun getDayForProgram(programId: Int, dayId: Int): Flow<Day>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Day)

   @Delete
   suspend fun delete(item: Day)

   @Update
   suspend fun update(item: Day)

}