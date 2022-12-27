package com.example.githubrepousers.app.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubrepousers.app.components.EmptyState
import com.example.githubrepousers.app.components.PrimaryChip
import com.example.githubrepousers.ui.theme.*

@Composable
fun ReposScreen() {
    Column {
//        EmptyState()
        RepoList()
    }
}


@Composable
fun RepoList() {

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
        RepoCard()
    }
}

@Composable
fun RepoCard() {
    Card(
        shape = RoundedCornerShape(DefaultBorderRadiusMedium),
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth(),
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
                    Text(text = "Kotlin DSL", fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.width(DefaultContentPadding))

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                        ){
                    Icon(
                        Icons.Rounded.Star, contentDescription = null,
                        tint = ColorLightGrey,
                        modifier = Modifier.size(DefaultIconSmall)
                    )

                    Spacer(modifier = Modifier.width(DefaultPaddingExtraSmall))
                    Text(text = "5", color = ColorLightGrey)

                }
            }

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = "Smart like Sheldon cooper? Join in", color = ColorLightGrey)

            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            PrimaryChip(
                backgroundColor = RepoChipBgColor,
                text = "javascript",
            )
            Spacer(modifier = Modifier.height(DefaultContentPaddingSmall))
            Text(text = "Updated 10/08/2022", color = ColorLightGrey)
        }
    }
}
