package com.myauko.engine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                    EnginePreviewScreen()
                }
            }
        }
    }
}

@Composable
fun EnginePreviewScreen(modifier: Modifier = Modifier) {
    val state = GameState.newGame()
    val prompt = PromptAssembler().assemble(
        PromptRequest(
            gameState = state,
            playerCommand = "showcase preview",
            moduleStyleTags = listOf("cinematic", "gothic architecture")
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "WH4K / Myauko Engine",
            style = MaterialTheme.typography.headlineMedium
        )
        Text("Manual build preview: app and core module are connected.")
        Text("Active module: ${state.activeModuleId}")
        Text("Visit: ${state.visitProgress.act.label}, frame ${state.visitProgress.frameIndex}")
        Text("Image provider layer is present. API key is not configured yet.")
        Text("Prompt preview:")
        Text(prompt.positivePrompt)
    }
}
