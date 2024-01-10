package com.example.alp.ui.navigation

import androidx.navigation.NavHostController

class AppActions(
    private val navController: NavHostController,
) {
    val navigateToEditFinance: (Int) -> Unit = { id: Int ->
        navController.navigate("${Destinations.MANAGE_FINANCE_ROUTE}/$id")
    }
}