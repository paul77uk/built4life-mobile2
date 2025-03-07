package com.example.built4life2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.built4life2.ui.navigation.NavigationHost
import com.example.built4life2.ui.theme.Built4Life2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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