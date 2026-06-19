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
    viewModel: VisitViewModel
) {
    var currentPrompt by remember { mutableStateOf("Visit started...") }
    var actFrameText by remember { mutableStateOf("Act 1 / Frame 1") }
    var visitStarted by remember { mutableStateOf(false) }

    // Auto-start the visit when screen opens
    LaunchedEffect(Unit) {
        if (!visitStarted) {
            // Start a demo visit (in real version this will come from module selection)
            val firstPrompt = viewModel.advanceFrame()
            currentPrompt = firstPrompt
            visitStarted = true
            
            // Update act/frame display
            val (act, frame) = viewModel.getCurrentActAndFrame()
            actFrameText = "Act $act / Frame $frame"
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

        // Image area
        Box(
            modifier = Modifier
                .size(320.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "[ Image will be generated here ]\n\n(Perchance / ModelsLab integration later)",
                color = MaterialTheme.colorScheme.outline,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
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
                val newPrompt = viewModel.advanceFrame("more foot focus")
                currentPrompt = newPrompt

                val (act, frame) = viewModel.getCurrentActAndFrame()
                actFrameText = "Act $act / Frame $frame"
            }) {
                Text("Next Frame")
            }

            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text("End Visit")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Demo mode — wh4k_myauko module active",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}