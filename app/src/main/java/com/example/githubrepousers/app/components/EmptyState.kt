package com.example.githubrepousers.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.githubrepousers.R
import com.example.githubrepousers.ui.theme.*

@Composable
fun EmptyState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(DefaultPaddingNormal))
        Spacer(modifier = Modifier.height(DefaultPaddingNormal))
        Text(
            text = stringResource(R.string.emptyStateInfo), style = TextStyle(
                fontSize = DefaultNormalFontSize,
                color = ColorGrey
            )
        )
        Spacer(modifier = Modifier.height(DefaultContentPadding))
        Image(
            painter = painterResource(id = R.drawable.empty_state_image),
            contentDescription = null,
            modifier = Modifier
                .width(EImageSize)
                .height(EImageSize)
                .clip(
                    shape = RoundedCornerShape(DefaultBorderRadiusMedium)
                )
        )
        Spacer(modifier = Modifier.height(DefaultContentPadding))
    }
}


@Composable
fun NoResultState(
    searchTerm: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(DefaultPaddingNormal))
        Spacer(modifier = Modifier.height(DefaultPaddingNormal))
        Text(
            text = stringResource(R.string.noResultStateInfo) + " $searchTerm",
            style = TextStyle(
                fontSize = DefaultNormalFontSize,
                color = ColorGrey
            )
        )
        Spacer(modifier = Modifier.height(DefaultContentPadding))
        Image(
            painter = painterResource(id = R.drawable.no_result_state),
            contentDescription = null,
            modifier = Modifier
                .width(EImageSize)
                .height(EImageSize)
                .clip(
                    shape = RoundedCornerShape(DefaultBorderRadiusMedium)
                )
        )
        Spacer(modifier = Modifier.height(DefaultContentPadding))
    }
}