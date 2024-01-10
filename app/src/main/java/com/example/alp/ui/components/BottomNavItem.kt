package com.example.alp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.alp.R
import com.example.alp.ui.navigation.Destinations

data class BottomNavItem(
    val name: String = "",
    val route: String? = null,
    val icon: Int = R.drawable.ic_pie_chart
){
    fun getItems(): List<BottomNavItem>{
        return listOf(
            BottomNavItem(
                name = "Home",
                route = Destinations.HOME_ROUTE,
                icon = R.drawable.ic_home
            ),
            BottomNavItem(
                name = "Budgeting",
                route = Destinations.BUDGETING_ROUTE,
                icon = R.drawable.ic_pie_chart
            ),
            BottomNavItem(
                name = "Split Bill",
                route = Destinations.CAMERA_ROUTE,
                icon = R.drawable.ic_camera
            ),
            BottomNavItem(
                name = "All Balance",
                route = Destinations.BALANCE_ROUTE,
                icon = R.drawable.ic_balance
            ),
            BottomNavItem(
                name = "Profile",
                route = Destinations.PROFILE_ROUTE,
                icon = R.drawable.ic_profile
            )
        )
    }
}