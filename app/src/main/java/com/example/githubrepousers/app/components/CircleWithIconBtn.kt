package com.example.githubrepousers.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.githubrepousers.ui.theme.*

@Composable
fun CircleIconButton(
    onTap: () -> Unit,
    icon: ImageVector,
    backgroundColor: Color = Color.White,
    color: Color = Color.Black
) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .size(DefaultIconNormal)
            .requiredHeight(DefaultIconNormal),
        backgroundColor = backgroundColor
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onTap,
                modifier = Modifier
                    .size(DefaultIconSmall)
                    .background(
                        color = LocalContentColor.current.copy(alpha = 0.0f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = icon,
                    contentDescription = null,
                    tint = color
                )
            }
        }

    }
}