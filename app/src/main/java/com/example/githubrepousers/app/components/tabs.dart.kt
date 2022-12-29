package com.example.githubrepousers.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.githubrepousers.app.models.Repo
import com.example.githubrepousers.app.models.User
import com.example.githubrepousers.app.network.UIState
import com.example.githubrepousers.app.pages.ReposScreen
import com.example.githubrepousers.app.pages.UsersScreen
import com.example.githubrepousers.ui.theme.ColorGrey
import com.example.githubrepousers.ui.theme.ColorLightGrey
import com.example.githubrepousers.ui.theme.ColorPrimary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = ColorGrey,
        edgePadding = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        divider = { Box { } },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = ColorPrimary,
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(tab.title, style = TextStyle(letterSpacing = 0.sp)) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                modifier = Modifier.padding(horizontal = 0.dp),
                selectedContentColor = ColorPrimary,
                unselectedContentColor = ColorLightGrey,
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    tabs: List<TabItem>, pagerState: PagerState,
    usersState: UIState<List<User?>>,
    usersList: List<User?>?,
    searchTerm: String,
    repoState: UIState<List<Repo?>>,
    reposList: List<Repo?>?,
    navController: NavHostController,
    searchRepoTerm: String,
) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        when (page) {
            0 -> UsersScreen(
                usersState = usersState,
                usersList = usersList,
                searchTerm = searchTerm,
                navController = navController
            )
            1 -> ReposScreen(
                repoState = repoState,
                reposList = reposList,
                searchTerm = searchRepoTerm,
                navController = navController,
            )
        }
    }
}
