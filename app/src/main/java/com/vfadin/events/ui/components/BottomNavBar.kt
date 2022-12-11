package com.vfadin.events.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vfadin.events.navigation.BottomNavigationScreen
import com.vfadin.events.ui.theme.TextBlack
import com.vfadin.events.ui.theme.White

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavigationScreen.Home,
        BottomNavigationScreen.Profile
    )
    BottomNavigation(
        backgroundColor = White,
        contentColor = TextBlack,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(screen.icon), contentDescription = null) },
                label = {
                    Text(
                        stringResource(screen.resourceId),
                        style = MaterialTheme.typography.subtitle2,
                        maxLines = 1
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}