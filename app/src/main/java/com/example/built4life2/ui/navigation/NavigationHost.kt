package com.example.built4life2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.built4life2.ui.screens.WorkoutListDestination
import com.example.built4life2.ui.screens.WorkoutListScreen

@Composable
fun NavigationHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = WorkoutListDestination.route,
    ) {
        composable(route = WorkoutListDestination.route) {
            WorkoutListScreen()
        }
    }
}