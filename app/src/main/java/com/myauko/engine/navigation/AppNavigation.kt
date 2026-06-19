package com.myauko.engine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myauko.engine.ui.screens.HubScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "hub") {
        composable("hub") {
            HubScreen(navController = navController)
        }
        // TODO: Add VisitScreen, Settings, etc.
    }
}