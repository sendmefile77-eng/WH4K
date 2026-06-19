package com.myauko.engine.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ShowcaseScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Showcase - Choose NPC", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        // Заглушки кандидаток
        Button(onClick = { navController.navigate("visit") }) {
            Text("Select NPC 1")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate("visit") }) {
            Text("Select NPC 2")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate("visit") }) {
            Text("Select NPC 3")
        }
    }
}