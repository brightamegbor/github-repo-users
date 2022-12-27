package com.example.githubrepousers.app.pages

import androidx.compose.foundation.layout.*
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubrepousers.app.components.PrimaryChip
import com.example.githubrepousers.ui.theme.*

@Composable
fun UsersScreen() {
    Column {
//        EmptyState()
        UserList()
    }
}

@Composable
fun UserList() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
                    append(" 30 results ")
                }
                append("for")
                withStyle(
                    style = SpanStyle(
                        color = Color.Black
                    )
                ) {
                    append(" chr")
                }
            }
        })
        Spacer(modifier = Modifier.height(DefaultContentPadding))

        // list
        UserCard()
    }
}

@Composable
fun UserCard() {
    Card(
        shape = RoundedCornerShape(DefaultBorderRadiusMedium),
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {

        Column(modifier = Modifier.padding(DefaultContentPadding)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(modifier = Modifier.weight(1f).wrapContentWidth(Alignment.Start)) {
                    Row {
                        Box(
                            modifier = Modifier
                                .width(UserAvatarSize)
                                .height(UserAvatarSize)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        "https://wallpaperaccess.com/full/137507.jpg"
                                    )
                                    .crossfade(true)
                                    .build(),
//                        placeholder = painterResource(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.width(DefaultContentPaddingSmall))
                        Text(text = "Christopher Marcus", fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.width(DefaultContentPadding))

                    }
                }

                Text(text = "1.2k followers", color = ColorLightGrey)
            }

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = "Engineering lead at effectstudios", color = ColorLightGrey)

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            PrimaryChip(
                backgroundColor = UserChipBgColor,
                text = "full stack",
            )
            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = "Accra, ghana", color = ColorLightGrey)
        }
    }
}
