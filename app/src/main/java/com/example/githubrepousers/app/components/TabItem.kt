package com.example.githubrepousers.app.components

sealed class TabItem(var title: String) {
    object Users : TabItem("Users")
    object Repositories : TabItem( "Repositories")
}
