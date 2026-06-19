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
    viewModel: VisitViewModel? = null   // TODO: inject properly via DI
) {
    var currentPrompt by remember { mutableStateOf("Visit started. Press Next Frame to begin.") }
    var actFrame by remember { mutableStateOf("Act 1 / Frame 1") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Visit - Cabin Company", style = MaterialTheme.typography.headlineSmall)
        Text(actFrame, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)

        Spacer(Modifier.height(24.dp))

        // Image placeholder
        Box(
            modifier = Modifier
                .size(320.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("[ Image will appear here ]", color = MaterialTheme.colorScheme.outline)
        }

        Spacer(Modifier.height(20.dp))

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
                val newPrompt = viewModel?.advanceFrame() ?: "No ViewModel"
                currentPrompt = newPrompt
                // Simple act/frame display update
                actFrame = "Act ? / Frame ?"
            }) {
                Text("Next Frame")
            }

            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text("End Visit")
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = "(PromptAssembler is connected)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}