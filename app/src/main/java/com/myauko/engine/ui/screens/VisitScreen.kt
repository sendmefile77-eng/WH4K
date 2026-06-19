package com.myauko.engine.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun VisitScreen(navController: NavController) {
    var currentPrompt by remember { mutableStateOf("Prompt frame placeholder") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Visit Screen (Act X / Frame Y)", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(280.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("Frame image placeholder")
        }

        Spacer(Modifier.height(16.dp))

        Text(currentPrompt, style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(24.dp))

        Row {
            Button(onClick = { currentPrompt = "Regenerate placeholder" }) {
                Text("Regenerate")
            }
            Spacer(Modifier.width(12.dp))
            Button(onClick = { currentPrompt = "Next frame placeholder" }) {
                Text("Next Frame")
            }
        }
    }
}
