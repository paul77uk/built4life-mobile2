package com.built4life.built4life2.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavigationBar(
        containerColor = Color.Black,
//        windowInsets = WindowInsets(
//            left = 4.dp,
//            right = 4.dp,
//        ),
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        val uriHandler = LocalUriHandler.current
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
                    if (navItem.title == "Community") {
                        uriHandler.openUri("https://discord.gg/Hjqkgs4Kan")
                    }
                    else {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
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