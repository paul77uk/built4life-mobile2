package com.example.built4life2.presentation.navigation

import com.example.built4life2.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Workouts",
            image = R.drawable.dumbbell,
            route = Route.WorkoutScreen.route
        ),
        BarItem(
            title = "Favorites",
            image = R.drawable.favorite,
            route = Route.FavoriteScreen.route
        ),
//        BarItem(
//            title = "Programs",
//            image = R.drawable.programs,
//            route = Route.ProgramScreen.route
//        ),
        BarItem(
            title = "Daily",
            image = R.drawable.date_range,
            route = Route.DailyScreen.route
        ),
//        BarItem(
//            title = "MONDAY",
//            image = R.drawable.date_range,
//            route = Route.MondayScreen.route
//        ),
//        BarItem(
//            title = "TUESDAY",
//            image = R.drawable.date_range,
//            route = Route.TuesdayScreen.route
//        ),
    )
}