package com.myauko.engine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myauko.engine.core.prompt.PromptAssembler
import com.myauko.engine.core.prompt.PromptRequest
import com.myauko.engine.core.state.GameState
import com.myauko.engine.ui.theme.MyaukoEngineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyaukoEngineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val baseState = remember { GameState.newGame() }
    var page by remember { mutableStateOf("Home") }
    var frame by remember { mutableIntStateOf(0) }
    var log by remember { mutableStateOf("Ready") }

    val prompt = remember(frame) {
        PromptAssembler().assemble(
            PromptRequest(
                gameState = baseState.copy(
                    visitProgress = baseState.visitProgress.copy(frameIndex = frame)
                ),
                playerCommand = "frame $frame",
                moduleStyleTags = listOf("cinematic", "gothic architecture")
            )
        ).positivePrompt
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "WH4K",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Myauko Engine",
            style = MaterialTheme.typography.titleLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Status", fontWeight = FontWeight.Bold)
                Text("Page: $page")
                Text("Module: ${baseState.activeModuleId}")
                Text("Frame: $frame")
                Text("Last action: $log")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Menu", fontWeight = FontWeight.Bold)
                Button(onClick = {
                    page = "Session"
                    frame = 0
                    log = "Session started"
                }) { Text("Start") }
                Button(onClick = {
                    page = "Modules"
                    log = "Module list opened"
                }) { Text("Modules") }
                Button(onClick = {
                    page = "Settings"
                    log = "Settings opened"
                }) { Text("Settings") }
                Button(onClick = {
                    page = "Frame"
                    frame += 1
                    log = "Frame generated"
                }) { Text("Frame") }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(page, fontWeight = FontWeight.Bold)
                when (page) {
                    "Home" -> Text("Use the menu to start a session or open a section.")
                    "Session" -> Text("Session is active. Press Frame to advance.")
                    "Modules" -> Text("Installed module: ${baseState.activeModuleId}")
                    "Settings" -> Text("Provider settings screen will be connected next.")
                    "Frame" -> {
                        Text("Current frame: $frame")
                        Text(prompt)
                    }
                }
                OutlinedButton(onClick = {
                    page = "Home"
                    log = "Returned home"
                }) { Text("Back home") }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Debug build v0.2")
    }
}
