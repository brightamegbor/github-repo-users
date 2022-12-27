package com.example.githubrepousers.app.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubrepousers.app.pages.RepoDetailsScreen
import com.example.githubrepousers.app.pages.UserDetailsScreen
import com.example.githubrepousers.app.pages.bottomnav.MainScreenView
import com.example.githubrepousers.app.view_models.MainViewModel

interface AppDestination {
    val route: String
}

sealed class AppRoutes : AppDestination {
    object Home : AppRoutes() {
        override val route = "home"
    }
    object UserDetails : AppRoutes() {
        override val route = "user-details"
    }
    object RepoDetails : AppRoutes() {
        override val route = "repo-details"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
) {

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Home.route,
        modifier = modifier
    ) {
        composable(route = AppRoutes.Home.route) { MainScreenView(
            mainViewModel = mainViewModel,
            mainNavController = navController,
        ) }
        composable(route = AppRoutes.UserDetails.route) { UserDetailsScreen(
//            navController = navController
        ) }
        composable(route = AppRoutes.RepoDetails.route) { RepoDetailsScreen(
//            navController = navController,
//            authViewModel = mainViewModel.authViewModel,
        ) }
    }

}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateTo(route: String) =  this.navigate(route)

