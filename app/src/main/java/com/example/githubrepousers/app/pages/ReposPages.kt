package com.example.githubrepousers.app.pages

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.githubrepousers.app.components.EmptyState
import com.example.githubrepousers.app.components.NoResultState
import com.example.githubrepousers.app.components.PrimaryChip
import com.example.githubrepousers.app.components.PrimaryLoader
import com.example.githubrepousers.app.helpers.Utils.Companion.toMomentAgo
import com.example.githubrepousers.app.models.Repo
import com.example.githubrepousers.app.network.UIState
import com.example.githubrepousers.ui.theme.*

@Composable
fun ReposScreen(repoState: UIState<List<Repo?>>, reposList: List<Repo?>?, searchTerm: String) {
    Box {
        when (repoState) {
            is UIState.Idle -> EmptyState()
            is UIState.Loading -> PrimaryLoader()
            else -> RepoList(reposList, searchTerm)
        }
    }
}


@Composable
fun RepoList(reposList: List<Repo?>?, searchTerm: String) {

    if (reposList.isNullOrEmpty())
        NoResultState(searchTerm)
    else LazyColumn {
        item {
            Spacer(modifier = Modifier.height(DefaultContentPadding))
            // title
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = ColorGrey)) {
                    withStyle(style = SpanStyle(color = ColorGrey)) {
                        append("Showing")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black
                        )
                    ) {
                        append("  ${reposList.size} results ")
                    }
                    append("for")
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
        items(reposList) { item ->
            RepoCard(item)
        }
    }
}

@Composable
fun RepoCard(item: Repo?) {
    Card(
        shape = RoundedCornerShape(DefaultBorderRadiusMedium),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = DefaultContentPaddingSmall),
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
                    Text(text = "${item?.score ?: 0}", color = ColorLightGrey)

                }
            }

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = item?.description ?: "", color = ColorLightGrey)

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            PrimaryChip(
                backgroundColor = RepoChipBgColor,
                text = item?.language ?: "",
            )
            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = "Updated ${toMomentAgo(item?.updatedAt)}", color = ColorLightGrey)
        }
    }
}
