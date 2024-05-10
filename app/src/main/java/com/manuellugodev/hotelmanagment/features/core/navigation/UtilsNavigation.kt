package com.manuellugodev.hotelmanagment.features.core.navigation

import androidx.navigation.NavController


fun NavController.navigateAndCleanBackStack(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}