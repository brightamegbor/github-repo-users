package com.example.githubrepousers.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.githubrepousers.app.helpers.navigateBack
import com.example.githubrepousers.ui.theme.*

@Composable
fun AppPage(
    navController: NavHostController,
    content: @Composable () -> Unit,
    title: String,
    bottomBar: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    GithubRepoUsersTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(ColorPrimary.copy(0.1f))
                        ) {

                            Box(modifier = Modifier.padding(DefaultPaddingNormalX)) {
                                CircleIconButton(
                                    onTap = { navController.navigateBack() },
                                    icon = Icons.Filled.ArrowBack,
                                    backgroundColor = ColorPrimary,
                                    color = colorWhite,
                                )
                            }
                        }
                    },
                    actions = {
                        if (actions != null) {
                            actions()
                        }
                    },
                    elevation = 0.dp,
                    backgroundColor = GlobalBgColor,
                    modifier = Modifier.padding(DefaultContentPadding),
                )
            },
            modifier = Modifier.statusBarsPadding(),
            bottomBar = bottomBar ?: { Box(modifier = Modifier.size(0.dp)) },
            backgroundColor = GlobalBgColor
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = DefaultPaddingMedium)
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                content()
            }
        }
    }
}