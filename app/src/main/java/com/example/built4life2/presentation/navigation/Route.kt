package com.example.built4life2.presentation.navigation


sealed class Route(val route: String) {
    data object WorkoutScreen : Route("workouts")
    data object FavoriteScreen : Route("favorites")
    data object ProgramScreen : Route("programs")
    data object DailyScreen : Route("daily")
//    data object MondayScreen : Route("MONDAY")
//    data object TuesdayScreen : Route("TUESDAY")
//    data object WednesdayScreen : Route("WEDNESDAY")
//    data object ThursdayScreen : Route("THURSDAY")
//    data object FridayScreen : Route("FRIDAY")
//    data object SaturdayScreen : Route("SATURDAY")
//    data object SundayScreen : Route("SUNDAY")

}