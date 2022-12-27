package com.example.githubrepousers.app.components

import com.example.githubrepousers.ui.theme.GithubRepoUsersTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.githubrepousers.ui.theme.colorBlack
import com.example.githubrepousers.ui.theme.colorWhite

@Composable
fun AppPage(
    navController: NavHostController,
    content: @Composable () -> Unit,
    title: String,
    bottomBar: @Composable() (() -> Unit)? = null
) {
    GithubRepoUsersTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack, "",
                                tint = colorBlack,
                            )
                        }
                    },
                    elevation = 1.dp,
                    backgroundColor = colorWhite,
                )
            },
            modifier = Modifier.statusBarsPadding(),
            bottomBar = bottomBar ?: {Box(modifier = Modifier.size(0.dp))}
        ) { padding ->
            Box(modifier = Modifier.padding(padding).navigationBarsPadding().imePadding()) {
                content()
            }
        }
    }
}