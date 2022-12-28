package com.example.githubrepousers.app.components

import androidx.compose.runtime.Composable


typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String,) {
    object Users : TabItem("Users",)
    object Repositories : TabItem( "Repositories",)
}
