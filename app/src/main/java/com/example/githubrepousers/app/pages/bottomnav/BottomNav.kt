package com.example.githubrepousers.app.pages.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.githubrepousers.R
import com.example.githubrepousers.app.sections.HomeScreen
import com.example.githubrepousers.app.view_models.MainViewModel
import com.example.githubrepousers.ui.theme.*

val items = listOf(
    BottomNavItem.Home,
    BottomNavItem.Analytics,
    BottomNavItem.Settings,
)

@Composable
fun MainScreenView(mainViewModel: MainViewModel, mainNavController: NavHostController) {
    val navController = rememberNavController()

    val navTitleSet = listOf<String>(
        "Home",
        "Analytics",
        "Settings",
    )

    var currentIndex by remember { mutableStateOf(0) }

    Scaffold(
        backgroundColor = GlobalBgColor,
        topBar = {
            TopAppBar(
                backgroundColor = GlobalBgColor,
                elevation = 0.dp,
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = DefaultContentPaddingSmall),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            navTitleSet[currentIndex],
                            color = ColorDarkGrey,
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = null,
                            tint = IconColorGrey,
                        )
                    }
                },
            )
        },
        modifier = Modifier.statusBarsPadding(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                    )
                    .background(
                        color = colorWhite,
                        shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
                    )
                    .fillMaxWidth(),
            ) {
                BottomNavigation(
                    navController = navController
                ) { value ->
                    println(value)
                    currentIndex = value
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
//                .navigationBarsPadding()
                .imePadding()
        ) {

            NavigationGraph(
                mainViewModel,
                navController = navController,
                mainNavController = mainNavController,
            )
        }

    }
}

@Composable
fun BottomNavigation(navController: NavController, onSelected: (Int) -> Unit) {

    BottomNavigation(
        backgroundColor = colorWhite,
        contentColor = Color.Black,
        elevation = 15.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.screen_route
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = DefaultFontSizeSmall
                    )
                },
                selectedContentColor = ColorPrimary,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onSelected(item.index)
                }
            )
//            }
        }
    }
}

@Composable
fun NavigationGraph(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    mainNavController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen(mainViewModel = mainViewModel, navController = mainNavController)
        }
        composable(BottomNavItem.Analytics.screen_route) {
            AnalyticsScreen(
            )
        }
        composable(BottomNavItem.Settings.screen_route) {
            SettingsScreen()
        }
    }
}

