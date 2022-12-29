package com.example.githubrepousers.app.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.githubrepousers.LocalActivity
import com.example.githubrepousers.R
import com.example.githubrepousers.app.components.EmptyState
import com.example.githubrepousers.app.components.NoResultState
import com.example.githubrepousers.app.components.PrimaryChip
import com.example.githubrepousers.app.components.PrimaryLoader
import com.example.githubrepousers.app.helpers.AppRoutes
import com.example.githubrepousers.app.helpers.Utils.Companion.toMomentAgo
import com.example.githubrepousers.app.helpers.navigateSingleTopTo
import com.example.githubrepousers.app.models.Repo
import com.example.githubrepousers.app.network.UIState
import com.example.githubrepousers.app.view_models.MainViewModel
import com.example.githubrepousers.app.view_models.RepoDetailsViewModel
import com.example.githubrepousers.ui.theme.*

@Composable
fun ReposScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current)
) {
    val repoState by remember { mainViewModel.repoState }.collectAsState()

    Box {
        when (repoState) {
            is UIState.Idle -> EmptyState()
            is UIState.Loading -> PrimaryLoader()
            else -> RepoList(
                navController = navController,
            )
        }
    }
}


@Composable
fun RepoList(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current)
) {
    val reposList by remember { mainViewModel.reposList }.collectAsState()
    val searchTerm by remember { mainViewModel.searchReposKeyword }.collectAsState()


    if (reposList.isNullOrEmpty())
        NoResultState(searchTerm!!)
    else LazyColumn {
        item {
            Spacer(modifier = Modifier.height(DefaultContentPadding))
            // title
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = ColorGrey)) {
                    withStyle(style = SpanStyle(color = ColorGrey)) {
                        append(stringResource(R.string.showing))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black
                        )
                    ) {
                        append("  ${reposList?.size} results ")
                    }
                    append(stringResource(R.string.forText))
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black
                        )
                    ) {
                        append(" $searchTerm")
                    }
                }
            })
            Spacer(modifier = Modifier.height(DefaultContentPadding))
        }

        // list
        items(reposList!!) { item ->
            RepoCard(item, navController = navController)
        }
    }
}

@Composable
fun RepoCard(
    item: Repo?,
    navController: NavHostController,
    repoDetailsViewModel: RepoDetailsViewModel = hiltViewModel(LocalActivity.current)
) {

    Card(
        shape = RoundedCornerShape(DefaultBorderRadiusMedium),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = DefaultContentPaddingSmall)
            .clickable {
                repoDetailsViewModel.updateRepoDetails(item)
                navController.navigateSingleTopTo(
                    "${AppRoutes.RepoDetails.route}/${item?.id}"
                )
            },
    ) {

        Column(modifier = Modifier.padding(DefaultContentPadding)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.Start)
                ) {
                    Text(text = item?.name ?: "", fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.width(DefaultContentPadding))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Rounded.Star, contentDescription = null,
                        tint = ColorLightGrey,
                        modifier = Modifier.size(DefaultIconSmall)
                    )

                    Spacer(modifier = Modifier.width(DefaultPaddingExtraSmall))
                    Text(text = "${item?.stargazersCount ?: 0}", color = ColorLightGrey)

                }
            }

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            item?.description?.let {
                Text(
                    text = it,
                    color = ColorLightGrey,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            item?.language?.let {
                PrimaryChip(
                    backgroundColor = RepoChipBgColor,
                    text = it,
                )
            }
            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = "Updated ${toMomentAgo(item?.updatedAt)}", color = ColorLightGrey)
        }
    }
}
