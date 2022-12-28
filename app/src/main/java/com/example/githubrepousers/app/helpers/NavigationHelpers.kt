package com.example.githubrepousers.app.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        override val route = "userDetails"
    }

    object RepoDetails : AppRoutes() {
        override val route = "repo_details"
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
        composable(route = AppRoutes.Home.route) {
            MainScreenView(
                mainViewModel = mainViewModel,
                mainNavController = navController,
            )
        }
        composable(route = "${AppRoutes.UserDetails.route}/{login}",
            arguments = listOf(
                navArgument("login") {
                    type = NavType.StringType
                }
            )
        ) {
            val login = it.arguments?.getString("login") ?: ""
            UserDetailsScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                login = login,
            )
        }
        composable(route = "${AppRoutes.RepoDetails.route}/{repoId}",
            arguments = listOf(
                navArgument("repoId") {
                    type = NavType.LongType
                }
            )
        ) {
            val repoId = it.arguments?.getLong("repoId") ?: 0
            RepoDetailsScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                repoId = repoId,
            )
        }
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

fun NavHostController.navigateTo(route: String) = this.navigate(route)

