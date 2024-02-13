package com.finance.hodl.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.finance.hodl.view.navigation.HodlNavGraph
import com.finance.hodl.view.navigation.HodlTopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberHodlAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): HodlAppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        HodlAppState(
            navController,
            coroutineScope
        )
    }
}

@Stable
class HodlAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: HodlTopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HodlNavGraph.Home.route -> HodlTopLevelDestination.HOME
            HodlNavGraph.Settings.route -> HodlTopLevelDestination.SETTINGS
            HodlNavGraph.Bots.route -> HodlTopLevelDestination.BOTS
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<HodlTopLevelDestination> = HodlTopLevelDestination.entries

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: HodlTopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            HodlTopLevelDestination.HOME -> navController.navigate(HodlNavGraph.Home.route)
            HodlTopLevelDestination.SETTINGS -> navController.navigate(HodlNavGraph.Settings.route)
            HodlTopLevelDestination.BOTS -> navController.navigate(HodlNavGraph.Bots.route)
        }
    }
}