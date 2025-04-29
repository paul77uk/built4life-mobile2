package com.built4life.built4life2.presentation

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.built4life.built4life2.Built4LifeApplication
import com.built4life.built4life2.presentation.daily.days.friday.FridayViewModel
import com.built4life.built4life2.presentation.daily.days.monday.MondayViewModel
import com.built4life.built4life2.presentation.daily.days.saturday.SaturdayViewModel
import com.built4life.built4life2.presentation.daily.days.sunday.SundayViewModel
import com.built4life.built4life2.presentation.daily.days.thursday.ThursdayViewModel
import com.built4life.built4life2.presentation.daily.days.tuesday.TuesdayViewModel
import com.built4life.built4life2.presentation.daily.days.wednesday.WednesdayViewModel
import com.built4life.built4life2.presentation.favorite.FavoriteViewModel
import com.built4life.built4life2.presentation.program.ProgramViewModel
import com.built4life.built4life2.presentation.workout.WorkoutViewModel


object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WorkoutViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            FavoriteViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            ProgramViewModel(
                built4LifeApplication().container.program,
                built4LifeApplication().container.day
            )
        }
        initializer {
            MondayViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            TuesdayViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            WednesdayViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            ThursdayViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            FridayViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            SaturdayViewModel(built4LifeApplication().container.workout)
        }
        initializer {
            SundayViewModel(built4LifeApplication().container.workout)
        }
    }
}

fun CreationExtras.built4LifeApplication(): Built4LifeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as Built4LifeApplication)
