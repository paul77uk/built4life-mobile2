package com.example.built4life2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.built4life2.designsystem.theme.Built4Life2Theme
import com.example.built4life2.presentation.navigation.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            Built4Life2Theme {
                CreateNav()
            }
        }
    }
}

@Composable
fun CreateNav(navController: NavHostController = rememberNavController()) {
    NavigationHost(navController = navController)
}