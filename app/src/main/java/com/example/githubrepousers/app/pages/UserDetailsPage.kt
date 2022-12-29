package com.example.githubrepousers.app.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubrepousers.R
import com.example.githubrepousers.app.components.AppPage
import com.example.githubrepousers.app.components.EmptyState
import com.example.githubrepousers.app.components.ErrorResultState
import com.example.githubrepousers.app.components.PrimaryLoader
import com.example.githubrepousers.app.helpers.Utils.Companion.formatNumber
import com.example.githubrepousers.app.network.UIState
import com.example.githubrepousers.app.view_models.UserDetailsViewModel
import com.example.githubrepousers.ui.theme.*


@Composable
fun UserDetailsScreen(
    navController: NavHostController,
    userDetailsViewModel: UserDetailsViewModel,
    login: String
) {

    val userDetailsState by remember { userDetailsViewModel.userDetailState }.collectAsState()
    val userRepoState by remember { userDetailsViewModel.userRepoState }.collectAsState()
    val userDetails by remember { userDetailsViewModel.userDetails }.collectAsState()
    val userReposList by remember { userDetailsViewModel.userReposList }.collectAsState()

    val scrollState = rememberScrollState()
    
    val isFirstTime = rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        if(isFirstTime.value) {
            userDetailsViewModel.fetchUserDetails(login)
            userDetailsViewModel.fetchUserRepos(login)

            isFirstTime.value = false
        }
    }

    AppPage(
        navController = navController,
        title = "",
        content = {
            when (userDetailsState) {
                is UIState.Idle -> EmptyState()
                is UIState.Loading -> PrimaryLoader()
                else -> Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    // header
                    Card(
                        elevation = 0.dp,
                        shape = RoundedCornerShape(DefaultBorderRadiusX),
                        backgroundColor = colorWhite,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(UserDetailAvatarHeight)
                                .padding(DefaultPaddingSmall)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        userDetails?.avatarURL
                                            ?: "https://wallpaperaccess.com/full/137507.jpg"
                                    )
                                    .crossfade(true)
                                    .build(),
//                        placeholder = painterResource(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(DefaultBorderRadiusX))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(DefaultContentPadding))
                    // title
                    Text(
                        text = userDetails?.name ?: "",
                        fontSize = DefaultTitleFontSize,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

                    userDetails?.bio?.let {
                        Text(
                            text = it,
                            fontSize = DefaultNormalFontSize,
                            color = ColorLightGrey
                        )
                    }
                    Spacer(modifier = Modifier.height(DefaultContentPadding))

                    //
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.profile_user),
                            contentDescription = null,
                            tint = ColorGrey,
                        )
                        Spacer(modifier = Modifier.width(DefaultContentPaddingSmall))
                        Text(
                            text = "${formatNumber(userDetails?.followers ?: 0)} followers",
                            fontSize = DefaultNormalFontSize,
                            color = ColorLightGrey
                        )
                        Spacer(modifier = Modifier.width(DefaultContentPaddingSmall))
                        Card(
                            modifier = Modifier.size(DefaultIconTiny),
                            shape = CircleShape,
                            backgroundColor = ColorGrey,
                            elevation = 0.dp

                        ) {}
                        Spacer(modifier = Modifier.width(DefaultContentPaddingSmall))
                        Text(
                            text = "${formatNumber(userDetails?.following ?: 0)}  Following",
                            fontSize = DefaultNormalFontSize,
                            color = ColorLightGrey
                        )
                    }

                    Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

                    userDetails?.location?.let {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.profile_user),
                                contentDescription = null,
                                tint = ColorGrey,
                            )
                            Spacer(modifier = Modifier.width(DefaultContentPaddingSmall))

                            Text(
                                text = it,
                                fontSize = DefaultNormalFontSize,
                                color = ColorLightGrey
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(DefaultPaddingMedium))

                    // tab like ui - or I could also use the tab component to achieve same
                    Box(modifier = Modifier.padding(start = DefaultPaddingSmall)) {

                        Text(
                            "Repositories",
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
                            modifier = Modifier.width(100.dp)
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

                    Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

                    //
                    when (userRepoState) {
                        is UIState.Loading -> PrimaryLoader()
                        is UIState.Error -> ErrorResultState()
                        else -> Column {
                            userReposList?.map {
                                RepoCard(item = it, navController)
                            }
                        }
                    }
                }
            }

        }
    )
}
