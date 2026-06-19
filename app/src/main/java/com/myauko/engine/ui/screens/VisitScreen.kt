package com.myauko.engine.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.myauko.engine.viewmodel.VisitViewModel

@Composable
fun VisitScreen(
    navController: NavController,
    viewModel: VisitViewModel
) {
    var currentPrompt by remember { mutableStateOf("Visit started... Generating first frame.") }
    var actFrameText by remember { mutableStateOf("Act 1 / Frame 1") }
    var commandInput by remember { mutableStateOf("") }
    var visitStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!visitStarted) {
            val firstPrompt = viewModel.advanceFrame()
            currentPrompt = firstPrompt
            visitStarted = true

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

        Spacer(Modifier.height(16.dp))

        // Image placeholder
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "[ Image Placeholder ]\n\nImage generation coming soon",
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(12.dp))

        // Prompt card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Current Prompt", style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.height(6.dp))
                Text(currentPrompt, style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(Modifier.height(16.dp))

        // Command input
        OutlinedTextField(
            value = commandInput,
            onValueChange = { commandInput = it },
            label = { Text("Player command (e.g. more foot focus, between breasts)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                val newPrompt = viewModel.advanceFrame(commandInput.ifBlank { null })
                currentPrompt = newPrompt

                val (act, frame) = viewModel.getCurrentActAndFrame()
                actFrameText = "Act $act / Frame $frame"

                commandInput = "" // clear after use
            }) {
                Text("Next Frame")
            }

            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text("End Visit")
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Type commands to influence the scene (foot focus works especially well)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}