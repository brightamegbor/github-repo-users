package com.example.githubrepousers.app.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.example.githubrepousers.R
import com.example.githubrepousers.app.components.PrimaryTextField
import com.example.githubrepousers.app.components.TabItem
import com.example.githubrepousers.app.components.Tabs
import com.example.githubrepousers.app.components.TabsContent
import com.example.githubrepousers.app.view_models.MainViewModel
import com.example.githubrepousers.ui.theme.DefaultContentPadding
import com.example.githubrepousers.ui.theme.DefaultContentPaddingSmall
import com.example.githubrepousers.ui.theme.IconColorGrey
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var searchField by remember {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }

    val tabs = listOf(
        TabItem.Users,
        TabItem.Repositories,
    )

    fun fetchUsersSuggestion(query: String) {
        coroutineScope.launch {
            mainViewModel.fetchUsers(query)
        }
    }

    fun fetchReposSuggestion(query: String) {
        coroutineScope.launch {
            mainViewModel.fetchRepos(query)
        }
    }

   fun onQueryChange(value: String) {
       coroutineScope.launch {
           if(pagerState.currentPage == 0) {
               fetchUsersSuggestion(value)
           } else {
               fetchReposSuggestion(value)
           }
       }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal =  DefaultContentPadding),
    ) {
        //
        PrimaryTextField(
            value = searchField,
            onValueChange = {
                searchField = it

                onQueryChange(it.text)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = IconColorGrey,
                )
            },
            placeholder = stringResource(R.string.search_placeholder),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        )

        Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

        // tabs
        Tabs(tabs = tabs, pagerState = pagerState)
        TabsContent(
            tabs = tabs, pagerState = pagerState,
            navController = navController
        )
    }
}
