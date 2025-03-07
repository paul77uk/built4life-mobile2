package com.example.built4life2.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.built4life2.Built4LifeApplication
import com.example.built4life2.ui.viewmodels.WorkoutViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WorkoutViewModel(built4LifeApplication().container.workoutDao)
        }
    }
}

fun CreationExtras.built4LifeApplication(): Built4LifeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as Built4LifeApplication)
