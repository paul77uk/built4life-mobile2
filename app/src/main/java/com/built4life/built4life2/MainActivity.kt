package com.built4life.built4life2

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.built4life.built4life2.ui.navigation.BottomNavBar
import com.built4life.built4life2.ui.navigation.NavGraph
import com.built4life.built4life2.ui.theme.Built4Life2Theme
import com.built4life.built4life2.ui.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Install Splash Screen - Keep as early as possible
        installSplashScreen()

        // 2. Configure Edge-to-Edge
        configureEdgeToEdge()

        // 3. Set up Composable UI
        setContent {
            MainScreen()
        }
    }

    private fun configureEdgeToEdge() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.BLACK, // Use black for status bar,
                Color.BLACK, // Use transparent for status bar
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.BLACK, // Use black for navigation bar,
                Color.BLACK, // Use black for navigation bar,, // Use transparent for navigation bar
            )
        )
    }

    @Composable
    fun MainScreen() {
        Built4Life2Theme {
            MainScaffold()
        }
    }

    @Composable
    fun MainScaffold() {
        val navController: NavHostController = rememberNavController()
        val workoutViewModel: WorkoutViewModel by viewModels()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavBar(navController = navController) },
        ) { paddingValues ->
            NavGraph(
                navController = navController,
                workoutViewModel = workoutViewModel,
                modifier = Modifier
                    .padding(paddingValues) //apply padding directly to the graph.
                    .consumeWindowInsets(paddingValues)
            )
        }
        // Keep screen on when app is in foreground
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}