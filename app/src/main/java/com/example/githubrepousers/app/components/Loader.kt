package com.example.githubrepousers.app.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.githubrepousers.ui.theme.ColorPrimary
import com.example.githubrepousers.ui.theme.DefaultLoaderSize
import com.example.githubrepousers.ui.theme.DefaultLoaderStroke


@Composable
fun PrimaryLoader(color: Color? = null
) {
    CircularProgressIndicator(
        strokeWidth = DefaultLoaderStroke,
        color = color ?: ColorPrimary,
        modifier = Modifier.size(DefaultLoaderSize),
    )
}
