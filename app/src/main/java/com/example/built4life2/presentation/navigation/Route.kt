package com.example.built4life2.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object WorkoutScreen : Route

    @Serializable
    data object ProgramScreen : Route
}