package com.built4life.built4life2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.built4life.built4life2.data.relation.ProgramWithDays
import com.built4life.built4life2.data.entity.Program
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDao {
    @Query("SELECT * from programs")
    fun getPrograms(): Flow<List<Program>>

    @Query("SELECT * from programs WHERE programId = :id")
    fun getProgram(id: Int): Flow<Program>

    @Transaction
    @Query("SELECT * from programs")
    fun getProgramsWithDays(): Flow<List<ProgramWithDays>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Program)

    @Update
    suspend fun update(item: Program)

    @Delete
    suspend fun delete(item: Program)

}