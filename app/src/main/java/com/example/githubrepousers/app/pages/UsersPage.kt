package com.example.githubrepousers.app.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubrepousers.app.components.EmptyState
import com.example.githubrepousers.app.components.NoResultState
import com.example.githubrepousers.app.components.PrimaryChip
import com.example.githubrepousers.app.components.PrimaryLoader
import com.example.githubrepousers.app.helpers.AppRoutes
import com.example.githubrepousers.app.helpers.Utils.Companion.toCapitalize
import com.example.githubrepousers.app.helpers.navigateSingleTopTo
import com.example.githubrepousers.app.models.User
import com.example.githubrepousers.app.network.UIState
import com.example.githubrepousers.ui.theme.*

@Composable
fun UsersScreen(
    navController: NavHostController,
    usersState: UIState<List<User?>>, usersList: List<User?>?, searchTerm: String
) {
    Box {
        when (usersState) {
            is UIState.Idle -> EmptyState()
            is UIState.Loading -> PrimaryLoader()
            else -> UserList(usersList, searchTerm, navController = navController)
        }
    }
}

@Composable
fun UserList(usersList: List<User?>?, searchTerm: String, navController: NavHostController) {

    if (usersList.isNullOrEmpty())
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
                        append(" ${usersList.size} results ")
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
        items(usersList) { item ->
            UserCard(item, navController = navController)
        }
    }
}

@Composable
fun UserCard(item: User?, navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(DefaultBorderRadiusMedium),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = DefaultContentPaddingSmall)
            .clickable {
                navController.navigateSingleTopTo(
                    "${AppRoutes.UserDetails.route}/${item?.login}"
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
                    Row {
                        Box(
                            modifier = Modifier
                                .width(UserAvatarSize)
                                .height(UserAvatarSize)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        item?.avatarURL
                                            ?: "https://wallpaperaccess.com/full/137507.jpg"
                                    )
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.width(DefaultContentPaddingSmall))
                        Text(
                            text = toCapitalize(item?.name ?: item?.login ?: ""),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(DefaultContentPadding))

                    }
                }

                Text(text = "${item?.followers ?: "1.2k"} followers", color = ColorLightGrey)
            }

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))

             Text(text = item?.bio ?: "Engineering lead at effect studios", color = ColorLightGrey)

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            PrimaryChip(
                backgroundColor = UserChipBgColor,
                text = "full stack",
            )
            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = item?.location ?: "Accra, ghana", color = ColorLightGrey)
        }
    }
}
