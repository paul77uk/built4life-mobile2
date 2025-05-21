package com.built4life.built4life2.di

import android.content.Context
import androidx.room.Room
import com.built4life.built4life2.data.Built4LifeDatabase
import com.built4life.built4life2.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        Built4LifeDatabase::class.java,
        DATABASE_NAME
    )
//        .createFromAsset(DATABASE_ASSET_PATH)
        .build()

    @Singleton
    @Provides
    fun workoutDao(database: Built4LifeDatabase) = database.workoutDao()

    @Singleton
    @Provides
    fun dayDao(database: Built4LifeDatabase) = database.dayDao()

    @Singleton
    @Provides
    fun programDao(database: Built4LifeDatabase) = database.programDao()

}