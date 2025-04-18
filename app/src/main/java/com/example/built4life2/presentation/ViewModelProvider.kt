package com.example.built4life2.presentation

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.built4life2.Built4LifeApplication
import com.example.built4life2.presentation.daily.DailyViewModel
import com.example.built4life2.presentation.daily.days.friday.FridayViewModel
import com.example.built4life2.presentation.daily.days.monday.MondayViewModel
import com.example.built4life2.presentation.daily.days.saturday.SaturdayViewModel
import com.example.built4life2.presentation.daily.days.sunday.SundayViewModel
import com.example.built4life2.presentation.daily.days.thursday.ThursdayViewModel
import com.example.built4life2.presentation.daily.days.tuesday.TuesdayViewModel
import com.example.built4life2.presentation.daily.days.wednesday.WednesdayViewModel
import com.example.built4life2.presentation.favorite.FavoriteViewModel
import com.example.built4life2.presentation.program.ProgramViewModel
import com.example.built4life2.presentation.workout.WorkoutViewModel


object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WorkoutViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            FavoriteViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            ProgramViewModel(
                built4LifeApplication().container.programDao,
                built4LifeApplication().container.dayDao
            )
        }
        initializer {
            DailyViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            MondayViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            TuesdayViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            WednesdayViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            ThursdayViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            FridayViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            SaturdayViewModel(built4LifeApplication().container.workoutDao)
        }
        initializer {
            SundayViewModel(built4LifeApplication().container.workoutDao)
        }
    }
}

fun CreationExtras.built4LifeApplication(): Built4LifeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as Built4LifeApplication)
