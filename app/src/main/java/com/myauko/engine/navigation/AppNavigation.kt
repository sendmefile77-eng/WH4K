package com.myauko.engine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myauko.engine.di.AppContainer
import com.myauko.engine.ui.screens.HubScreen
import com.myauko.engine.ui.screens.VisitScreen
import com.myauko.engine.viewmodel.VisitViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "hub") {
        composable("hub") {
            HubScreen(navController = navController)
        }
        composable("visit") {
            // Create ViewModel using our simple DI container
            val visitManager = AppContainer.visitManager
            val viewModel = VisitViewModel(visitManager)
            VisitScreen(navController = navController, viewModel = viewModel)
        }
    }
}