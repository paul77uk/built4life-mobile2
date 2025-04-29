package com.built4life.built4life2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.built4life.built4life2.presentation.community.CommunityScreen
import com.built4life.built4life2.presentation.daily.DailyScreen
import com.built4life.built4life2.presentation.favorite.FavoriteScreen
import com.built4life.built4life2.presentation.program.ProgramScreen
import com.built4life.built4life2.presentation.workout.WorkoutScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        modifier = modifier,
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
            DailyScreen()
        }

        composable(Route.CommunityScreen.route) {
            CommunityScreen()
        }


    }
}