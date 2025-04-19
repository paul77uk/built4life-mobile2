package com.example.built4life2.presentation.navigation


sealed class Route(val route: String) {
    data object WorkoutScreen : Route("workouts")
    data object FavoriteScreen : Route("favorites")
    data object ProgramScreen : Route("programs")
    data object DailyScreen : Route("daily")
}