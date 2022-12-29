package com.example.githubrepousers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.githubrepousers.app.helpers.AppNavHost
import com.example.githubrepousers.app.view_models.MainViewModel
import com.example.githubrepousers.app.view_models.RepoDetailsViewModel
import com.example.githubrepousers.app.view_models.UserDetailsViewModel
import com.example.githubrepousers.ui.theme.GithubRepoUsersTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("LocalActivity is not present")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
            }
            CompositionLocalProvider(LocalActivity provides this@MainActivity) {
                GithubRepoUsersTheme {
                    GithubRepoApp()
                }
            }

        }
    }
}

@Composable
fun GithubRepoApp() {
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val userDetailsViewModel = hiltViewModel<UserDetailsViewModel>()
    val repoDetailsViewModel = hiltViewModel<RepoDetailsViewModel>()

    Scaffold { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            mainViewModel = mainViewModel,
            userDetailsViewModel = userDetailsViewModel,
            repoDetailsViewModel =  repoDetailsViewModel
        )

    }
}
