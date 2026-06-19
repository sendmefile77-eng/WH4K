package com.wh4k.myauko

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
import com.wh4k.myauko.core.prompt.PromptAssembler
import com.wh4k.myauko.core.prompt.PromptRequest
import com.wh4k.myauko.core.state.GameState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyaukoApp()
        }
    }
}

@Composable
fun MyaukoApp() {
    val state = GameState.newGame()
    val previewPrompt = PromptAssembler().assemble(
        PromptRequest(
            gameState = state,
            playerCommand = "cinematic grimdark showcase",
            moduleStyleTags = listOf("grimdark", "cinematic", "android-compose-preview")
        )
    )

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("WH4K / Myauko Engine", style = MaterialTheme.typography.headlineMedium)
                Text("Core state: ${state.visitProgress.act.label} / frame ${state.visitProgress.frameIndex}")
                Text("Atlas Cloud prompt preview:")
                Text(previewPrompt.positivePrompt)
            }
        }
    }
}
