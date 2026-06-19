package com.myauko.engine.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.myauko.engine.viewmodel.VisitViewModel

@Composable
fun VisitScreen(
    navController: NavController,
    viewModel: VisitViewModel? = null
) {
    var currentPrompt by remember { mutableStateOf("Visit started. Generating first frame...") }
    var actFrameText by remember { mutableStateOf("Act 1 / Frame 1") }
    var hasStarted by remember { mutableStateOf(false) }

    // Auto-start visit on first composition (demo mode)
    LaunchedEffect(Unit) {
        if (!hasStarted) {
            // In real version this should come from selected module + NPC
            // For now we just trigger first frame
            val firstPrompt = viewModel?.advanceFrame() ?: "Demo mode: No ViewModel connected yet"
            currentPrompt = firstPrompt
            hasStarted = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Visit - Cabin Company", style = MaterialTheme.typography.headlineSmall)
        Text(actFrameText, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)

        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(320.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("[ Image Placeholder ]\n\nImage generation will be connected here", color = MaterialTheme.colorScheme.outline)
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Current Prompt:", style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.height(8.dp))
                Text(currentPrompt, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                val newPrompt = viewModel?.advanceFrame("more foot focus") ?: "Next frame (demo)"
                currentPrompt = newPrompt
                actFrameText = "Act ? / Frame ? (updated)"
            }) {
                Text("Next Frame")
            }

            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text("End Visit")
            }
        }

        Spacer(Modifier.height(20.dp))
        Text(
            text = "Demo mode — Full data + DI coming next",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}