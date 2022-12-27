package com.example.githubrepousers.app.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
) {

    val scrollState = rememberScrollState()
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

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(DefaultContentPadding),
    ) {
        //
        PrimaryTextField(
            value = searchField,
            onValueChange = {
                searchField = it
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = IconColorGrey,
                )
            },
//            label = "",
            placeholder = stringResource(R.string.search_placeholder),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        )

        Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

        // tabs
        Tabs(tabs = tabs, pagerState = pagerState)
        TabsContent(
            tabs = tabs, pagerState = pagerState,
            mainViewModel = mainViewModel,
//            allListing = allListing,
//            activeListing = activeListing,
        )
    }
}