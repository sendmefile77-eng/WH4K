package com.myauko.engine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myauko.engine.ui.screens.HubScreen
import com.myauko.engine.ui.screens.VisitScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "hub") {
        composable("hub") {
            HubScreen(navController = navController)
        }
        composable("visit") {
            VisitScreen(navController = navController)
        }
        // TODO: Add DirectionSelection, Settings, ModuleSelection
    }
}