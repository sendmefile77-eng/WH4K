package com.myauko.engine.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DirectionSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Choose Direction", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        // Заглушки направлений из модуля
        Button(onClick = { navController.navigate("showcase") }) {
            Text("Commissariat")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate("showcase") }) {
            Text("Cadian Guard")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate("showcase") }) {
            Text("Slaanesh Cult")
        }
    }
}