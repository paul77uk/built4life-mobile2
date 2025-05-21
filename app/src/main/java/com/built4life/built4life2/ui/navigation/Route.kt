package com.built4life.built4life2.ui.navigation


sealed class Route(val route: String) {
    data object WorkoutScreen : Route("workouts")
    data object FavoriteScreen : Route("favorites")
    data object ProgramScreen : Route("programs")
    data object DailyScreen : Route("daily")
    data object CommunityScreen : Route("community")
}