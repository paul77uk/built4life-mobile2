package com.example.built4life2

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.built4life2.designsystem.theme.Built4Life2Theme
import com.example.built4life2.presentation.navigation.NavBarItems
import com.example.built4life2.presentation.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.BLACK,
            ),
        )
        setContent {
            Built4Life2Theme {
                RootLayout()
            }
        }
    }
}

@Composable
fun RootLayout(
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = androidx.compose.ui.graphics.Color.Black,
            ) {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                NavBarItems.BarItems.forEach { navItem ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painterResource(navItem.image),
                                contentDescription = navItem.title
                            )
                        },
                        label = {
                            Text(navItem.title)
                        },
                        selected = currentRoute == navItem.route,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = androidx.compose.ui.graphics.Color.Black,
                            selectedIconColor = androidx.compose.ui.graphics.Color.White,
                            selectedTextColor = androidx.compose.ui.graphics.Color.White,
                            unselectedIconColor = androidx.compose.ui.graphics.Color.Gray,
                            unselectedTextColor = androidx.compose.ui.graphics.Color.Gray,
                            disabledIconColor = androidx.compose.ui.graphics.Color.Gray,
                            disabledTextColor = androidx.compose.ui.graphics.Color.Gray,
                        )
                    )
                }
            }
        }
    ) { paddingValue ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValue
        )
    }
}