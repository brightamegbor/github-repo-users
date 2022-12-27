package com.example.githubrepousers.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.githubrepousers.ui.theme.DefaultBorderRadiusMedium
import com.example.githubrepousers.ui.theme.DefaultContentPaddingSmall
import com.example.githubrepousers.ui.theme.DefaultPaddingExtraSmall

@Composable
fun PrimaryChip(
    backgroundColor: Color,
    text: String
) {
    Card(
        shape = RoundedCornerShape(
            DefaultBorderRadiusMedium
        ),
        backgroundColor = backgroundColor.copy(alpha = 0.1f),
        elevation = 0.dp,
    ) {
        Box(
            modifier = Modifier
                .padding(DefaultContentPaddingSmall, DefaultPaddingExtraSmall)
        ) {

            Text(text = text, color = backgroundColor)
        }
    }
}