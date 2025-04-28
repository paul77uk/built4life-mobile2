package com.built4life.built4life2

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.built4life.built4life2.presentation.navigation.BottomNavBar
import com.built4life.built4life2.presentation.navigation.NavGraph
import com.built4life.built4life2.presentation.theme.Built4Life2Theme

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
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT, // Use transparent for status bar
            ),
            navigationBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT, // Use transparent for navigation bar
            )
        )
    }

    @Composable
    fun MainScreen() {
        Built4Life2Theme {
            val navController: NavHostController = rememberNavController()
            MainScaffold(navController = navController)
        }
    }

    @Composable
    fun MainScaffold(navController: NavHostController) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavBar(navController = navController) },
        ) { paddingValues ->
            NavGraph(
                navController = navController,
                modifier = Modifier
                    .padding(paddingValues) //apply padding directly to the graph.
                    .consumeWindowInsets(paddingValues)
            )
        }
    }
}