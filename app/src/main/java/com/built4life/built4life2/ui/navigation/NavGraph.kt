package com.built4life.built4life2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.built4life.built4life2.ui.screen.daily.DailyScreen
import com.built4life.built4life2.ui.screen.favorite.FavoriteScreen
import com.built4life.built4life2.ui.screen.workout.WorkoutScreen
import com.built4life.built4life2.ui.viewmodel.WorkoutViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel,
    modifier: Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.WorkoutScreen.route
    ) {
        composable(Route.WorkoutScreen.route) {
            WorkoutScreen(
                workoutViewModel = workoutViewModel,
            )
        }

        composable(Route.FavoriteScreen.route) {
            FavoriteScreen(workoutViewModel = workoutViewModel)
        }

//        composable(Route.ProgramScreen.route) {
//            ProgramScreen()
//        }

        composable(Route.DailyScreen.route) {
            DailyScreen(workoutViewModel)
        }

//        composable(Route.CommunityScreen.route) {
//            CommunityScreen()
//        }


    }
}