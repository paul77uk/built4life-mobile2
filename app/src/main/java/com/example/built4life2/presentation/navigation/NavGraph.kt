package com.example.built4life2.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.built4life2.presentation.daily.DailyScreen
import com.example.built4life2.presentation.favorite.FavoriteScreen
import com.example.built4life2.presentation.program.ProgramScreen
import com.example.built4life2.presentation.workout.WorkoutScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues),
        navController = navController,
        startDestination = Route.WorkoutScreen.route
    ) {
        composable(Route.WorkoutScreen.route) {
            WorkoutScreen(
            )
        }

        composable(Route.FavoriteScreen.route) {
            FavoriteScreen(
            )
        }

        composable(Route.ProgramScreen.route) {
            ProgramScreen()
        }

        composable(Route.DailyScreen.route) {
            DailyScreen(navController = navController)
        }

    }
}