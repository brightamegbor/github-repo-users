package com.example.githubrepousers.app.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.githubrepousers.R
import com.example.githubrepousers.app.components.AppPage
import com.example.githubrepousers.app.components.PrimaryChip
import com.example.githubrepousers.app.components.PrimaryLoader
import com.example.githubrepousers.app.helpers.Utils.Companion.toCapitalize
import com.example.githubrepousers.app.helpers.Utils.Companion.toMomentAgo
import com.example.githubrepousers.app.network.UIState
import com.example.githubrepousers.app.view_models.RepoDetailsViewModel
import com.example.githubrepousers.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun RepoDetailsScreen(
    navController: NavHostController,
    repoId: Long,
    repoDetailsViewModel: RepoDetailsViewModel
) {
    val repoDetails by remember { repoDetailsViewModel.reposDetails }.collectAsState()
    val repoLanguagesState by remember { repoDetailsViewModel.repoLanguagesState }.collectAsState()
    val repoLanguages by remember { repoDetailsViewModel.repoLanguages }.collectAsState()

    val scrollState = rememberScrollState()

    var total = 0.0

    repoLanguages?.entries?.forEach {
        total += it.value
    }

    LaunchedEffect(Unit) {
        repoDetailsViewModel.fetchRepoLanguages(
            login = repoDetails?.owner?.login ?: "",
            repo = repoDetails?.name ?: "",
        )
    }

    fun calculatePercent(value: Long): String {
        if (total != 0.0) {
            var result = ((value / total) * 100)
            result = String.format("%.1f", result).toDouble()

            return "$result%"
        }

        return ""

    }

    fun calculateFloat(value: Long): Float {
        if (total != 0.0) {
            val result = ((value / total) * 100)

            return (result / 100).toFloat()
        }

        return 1f
    }

    AppPage(
        navController = navController,
        title = "",
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Rounded.Star, contentDescription = null,
                    tint = ColorLightGrey,
                    modifier = Modifier.size(DefaultIconSmall)
                )
                Spacer(modifier = Modifier.width(DefaultPaddingExtraSmall))
                Text(text = "${repoDetails?.stargazersCount ?: 0}", color = ColorLightGrey)
            }
        },
        content = {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                // title
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            text = repoDetails?.name ?: "",
                            fontSize = DefaultTitleFontSize,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.width(DefaultPaddingNormal))

                    // or use repoDetails.visibility - only public repos are returned
                    PrimaryChip(
                        backgroundColor = ColorPrimary,
                        text = toCapitalize(repoDetails?.visibility ?: "public")
                    )
                }

                Spacer(modifier = Modifier.height(DefaultPaddingNormal))
                Spacer(modifier = Modifier.height(DefaultPaddingNormal))

                // custom tab like ui - or I could also use the tab component to achieve same
                Box(modifier = Modifier.padding(start = DefaultPaddingSmall)) {

                    Text(
                        "About",
                        color = ColorPrimary,
                    )
                }

                Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(
                        color = ColorPrimary,
                        startIndent = 0.dp,
                        thickness = 2.dp,
                        modifier = Modifier.width(60.dp)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    ) {
                        Divider(
                            color = ColorLightGrey.copy(0.2f),
                            thickness = 2.dp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(DefaultContentPadding))

                //
                Text(
                    "Description",
                    color = ColorLightGrey,
                )

                Spacer(modifier = Modifier.height(DefaultContentPadding))

                repoDetails?.description?.let {
                    Text(
                        it,
                        color = ColorLightGrey,
                    )
                }

                Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

                if (repoLanguages?.isNotEmpty() == true)
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = DefaultPaddingSmall),
                    ) {
                        repoLanguages?.entries?.map {
                            Box(
                                modifier = Modifier.padding(
                                    end = DefaultContentPaddingSmall,
                                    bottom = DefaultContentPaddingSmall
                                )
                            ) {
                                PrimaryChip(backgroundColor = ColorPrimary, text = it.key)
                            }
                        }
                    }
                else repoDetails?.language?.let {
                    PrimaryChip(
                        backgroundColor = ColorPrimary,
                        text = it
                    )
                }

                Spacer(modifier = Modifier.height(DefaultPaddingMedium))

                Text(
                    stringResource(R.string.releases),
                    color = ColorLightGrey,
                )

                Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

                Text(
                    stringResource(R.string.no_releases_info),
                    color = ColorLightGrey,
                )
                Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

                Text(text = toMomentAgo(repoDetails?.updatedAt), color = ColorLightGrey)

                Spacer(modifier = Modifier.height(DefaultPaddingNormal))
                Spacer(modifier = Modifier.height(DefaultPaddingNormal))

                Text(
                    stringResource(R.string.languages),
                    color = ColorLightGrey,
                    fontSize = DefaultNormalFontSize
                )

                Spacer(modifier = Modifier.height(DefaultContentPadding))

                // languages bar with %
                when (repoLanguagesState) {
                    is UIState.Loading -> PrimaryLoader()
                    is UIState.Success -> if(repoLanguages?.isNotEmpty() == true) Column {

                        // draw bar with multi colors, need to provide more colors for others
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(DefaultPaddingNormal),
                            shape = RoundedCornerShape(DefaultBorderRadiusNormal),
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp
                            ) {
                            Row(modifier = Modifier.fillMaxSize()) {
                                repoLanguages?.entries?.map {
                                    Box(
                                        modifier = Modifier
                                            .background(getLanguageColor(it.key))
                                            .fillMaxSize()
                                            .weight(calculateFloat(it.value))
                                    )
                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(DefaultContentPadding))
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = DefaultPaddingSmall),
                        ) {
                            repoLanguages?.entries?.map {
                                Box(
                                    modifier = Modifier.padding(
                                        end = DefaultContentPaddingSmall,
                                        bottom = DefaultContentPaddingSmall
                                    )
                                ) {
                                    PrimaryChip(
                                        backgroundColor = getLanguageColor(it.key),
                                        text = "${it.key} ${calculatePercent(it.value)}"
                                    )
                                }
                            }
                        }
                    } else Box {}

                    else -> Box {}
                }
            }


        }
    )
}

fun getLanguageColor(key: String): Color {
    val jsColor = Color(0xFF9747FF)
    val cssColor = Color(0xFFF1E05A)
    val htmlColor = Color(0xFFEF9D65)
    val goColor = ColorGrey

    return when(key.trim().lowercase()) {
        "javascript" -> jsColor
        "css" -> cssColor
        "html" -> htmlColor
        "go" -> goColor
        "c" -> Color.Cyan
        "python" -> Color.Red
        else -> ColorPrimary
    }

}
