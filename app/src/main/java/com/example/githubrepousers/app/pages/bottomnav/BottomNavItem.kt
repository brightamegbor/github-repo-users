package com.example.githubrepousers.app.pages.bottomnav

import com.example.githubrepousers.R

sealed class BottomNavItem(var index:Int, var icon: Int, var screen_route:String, var title:String){

    object Home : BottomNavItem(0, R.drawable.ic_home,"home", "Home")
    object Analytics: BottomNavItem(1, R.drawable.ic_user,"analytics", "Analytics")
    object Settings: BottomNavItem(2, R.drawable.ic_setting,"settings", "Settings")
}
