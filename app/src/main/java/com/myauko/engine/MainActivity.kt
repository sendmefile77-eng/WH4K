package com.myauko.engine

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myauko.engine.core.aihorde.AiHordeProvider
import com.myauko.engine.core.image.ImageGenerationRequest
import com.myauko.engine.core.prompt.PromptAssembler
import com.myauko.engine.core.prompt.PromptRequest
import com.myauko.engine.core.state.GameState
import com.myauko.engine.ui.theme.MyaukoEngineTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyaukoEngineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val baseState = remember { GameState.newGame() }
    val provider = remember { AiHordeProvider() }

    var screen by remember { mutableStateOf("Hub") }
    var active by remember { mutableStateOf(false) }
    var module by remember { mutableStateOf("example.grimdark") }
    var zone by remember { mutableStateOf("Gate") }
    var frame by remember { mutableIntStateOf(0) }
    var hp by remember { mutableIntStateOf(10) }
    var focus by remember { mutableIntStateOf(4) }
    var heat by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var ending by remember { mutableStateOf("") }
    var sceneText by remember { mutableStateOf("Press Start to begin a run.") }
    var frameCard by remember { mutableStateOf("No frame rendered yet.") }
    var imageStatus by remember { mutableStateOf("No image generated yet.") }
    var imageData by remember { mutableStateOf<String?>(null) }
    var generating by remember { mutableStateOf(false) }

    val log = remember { mutableStateListOf("Loaded") }

    fun record(value: String) {
        log.add(0, value)
        if (log.size > 8) log.removeAt(log.lastIndex)
    }

    fun setScene(value: String) {
        sceneText = value
        frameCard = "$zone / frame $frame / focus $focus / heat $heat"
        imageData = null
        imageStatus = "No image for this frame yet."
        record(value)
    }

    fun checkEnd() {
        ending = when {
            hp <= 0 -> "Run failed. HP reached zero."
            score >= 20 -> "Run complete. Objective secured."
            heat >= 12 -> "Run failed. Heat reached maximum."
            else -> ""
        }
        if (ending.isNotBlank()) {
            active = false
            screen = "End"
            record(ending)
        }
    }

    fun nextFrame(action: String, hpDelta: Int, focusDelta: Int, heatDelta: Int, scoreDelta: Int) {
        if (!active) return
        frame += 1
        hp = (hp + hpDelta).coerceIn(0, 12)
        focus = (focus + focusDelta).coerceIn(0, 10)
        heat = (heat + heatDelta).coerceIn(0, 12)
        score += scoreDelta
        screen = "Scene"
        setScene("Frame $frame: $action")
        checkEnd()
    }

    val promptResult = remember(frame, zone, hp, focus, heat, score, module) {
        PromptAssembler().assemble(
            PromptRequest(
                gameState = baseState.copy(
                    activeModuleId = module,
                    visitProgress = baseState.visitProgress.copy(frameIndex = frame)
                ),
                playerCommand = "zone $zone frame $frame hp $hp focus $focus heat $heat score $score",
                moduleStyleTags = listOf("cinematic", "gothic architecture")
            )
        )
    }

    fun normalizeImageData(raw: String): String {
        return raw
            .removePrefix("data:image/png;base64,")
            .removePrefix("data:image/jpeg;base64,")
            .removePrefix("data:image/webp;base64,")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("WH4K", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Text("Myauko Engine playable build", style = MaterialTheme.typography.titleMedium)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Run Status", fontWeight = FontWeight.Bold)
                Text("Screen: $screen")
                Text("Run: ${if (active) "active" else "idle"}")
                Text("Module: $module")
                Text("Zone: $zone")
                Text("Frame: $frame")
                Text("HP: $hp / 12")
                LinearProgressIndicator(progress = hp / 12f, modifier = Modifier.fillMaxWidth())
                Text("Focus: $focus / 10")
                LinearProgressIndicator(progress = focus / 10f, modifier = Modifier.fillMaxWidth())
                Text("Heat: $heat / 12")
                LinearProgressIndicator(progress = heat / 12f, modifier = Modifier.fillMaxWidth())
                Text("Score: $score / 20")
                LinearProgressIndicator(progress = (score.coerceAtMost(20)) / 20f, modifier = Modifier.fillMaxWidth())
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                active = true
                ending = ""
                screen = "Scene"
                zone = "Gate"
                frame = 1
                hp = 10
                focus = 4
                heat = 0
                score = 0
                setScene("Frame 1: You enter the first zone.")
            }) { Text("Start") }

            Button(onClick = {
                screen = "Map"
                record("Map opened")
            }) { Text("Map") }

            Button(onClick = {
                screen = "Log"
                record("Log opened")
            }) { Text("Log") }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = {
                screen = "Settings"
                record("Settings opened")
            }) { Text("Settings") }

            OutlinedButton(onClick = {
                nextFrame("Prepared next frame", hpDelta = 0, focusDelta = 0, heatDelta = 1, scoreDelta = 1)
            }) { Text("Next") }
        }

        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(screen, fontWeight = FontWeight.Bold)

                when (screen) {
                    "Hub" -> {
                        Text("Start a run or open a section.")
                    }

                    "Scene" -> {
                        Text(sceneText)
                        Spacer(Modifier.height(4.dp))
                        Text("Frame card", fontWeight = FontWeight.Bold)
                        Text(frameCard)

                        Spacer(Modifier.height(6.dp))
                        Text("Image", fontWeight = FontWeight.Bold)
                        Text(imageStatus)

                        val imageBase64 = imageData
                        if (imageBase64 != null) {
                            val bytes = remember(imageBase64) {
                                Base64.decode(normalizeImageData(imageBase64), Base64.DEFAULT)
                            }
                            val bitmap = remember(bytes) {
                                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            }
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Generated frame",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        Button(
                            enabled = !generating && active,
                            onClick = {
                                scope.launch {
                                    generating = true
                                    imageStatus = "Submitting image request..."
                                    try {
                                        val request = ImageGenerationRequest(
                                            prompt = promptResult,
                                            width = 512,
                                            height = 768,
                                            steps = 24,
                                            guidanceScale = 7.0f,
                                            allowAdultFictionalContent = true
                                        )
                                        val queued = provider.generate(request)
                                        imageStatus = "Queued: ${queued.id}"

                                        repeat(40) {
                                            delay(3000)
                                            val status = provider.status(queued.id)
                                            val img = status.generations.firstOrNull()?.img
                                            if (!img.isNullOrBlank()) {
                                                imageData = img
                                                imageStatus = "Image ready."
                                                generating = false
                                                return@launch
                                            }
                                            imageStatus = "Waiting... jobs: ${status.waiting}, processing: ${status.processing}"
                                        }

                                        imageStatus = "Still queued. Try again later."
                                    } catch (e: Exception) {
                                        imageStatus = "Image error: ${e.message ?: "unknown"}"
                                    } finally {
                                        generating = false
                                    }
                                }
                            }
                        ) { Text(if (generating) "Generating..." else "Generate image") }

                        Spacer(Modifier.height(4.dp))
                        Text("Actions", fontWeight = FontWeight.Bold)

                        Button(onClick = {
                            nextFrame("Careful search. You gain score and focus.", hpDelta = 0, focusDelta = 1, heatDelta = 1, scoreDelta = 2)
                        }) { Text("Careful search") }

                        Button(onClick = {
                            nextFrame("Fast advance. You gain more score but lose HP.", hpDelta = -1, focusDelta = 0, heatDelta = 2, scoreDelta = 4)
                        }) { Text("Fast advance") }

                        Button(onClick = {
                            nextFrame("Hold position. You recover HP and reduce heat.", hpDelta = 1, focusDelta = 0, heatDelta = -2, scoreDelta = 0)
                        }) { Text("Hold position") }
                    }

                    "Map" -> {
                        Text("Choose zone.")
                        Button(onClick = {
                            zone = "Gate"
                            screen = "Scene"
                            setScene("Moved to Gate.")
                        }) { Text("Gate") }

                        Button(onClick = {
                            zone = "Market"
                            screen = "Scene"
                            heat += 1
                            setScene("Moved to Market.")
                            checkEnd()
                        }) { Text("Market") }

                        Button(onClick = {
                            zone = "Chapel"
                            screen = "Scene"
                            focus += 1
                            heat += 2
                            setScene("Moved to Chapel.")
                            checkEnd()
                        }) { Text("Chapel") }
                    }

                    "Settings" -> {
                        Text("Module selector.")
                        Button(onClick = {
                            module = "example.grimdark"
                            record("Module selected: example.grimdark")
                        }) { Text("example.grimdark") }

                        Button(onClick = {
                            module = "test.citadel"
                            record("Module selected: test.citadel")
                        }) { Text("test.citadel") }

                        Text("AI Horde anonymous mode is used by default.")
                    }

                    "Log" -> {
                        log.forEach { Text("- $it") }
                    }

                    "End" -> {
                        Text(ending)
                        Button(onClick = {
                            active = true
                            ending = ""
                            screen = "Scene"
                            frame = 1
                            hp = 10
                            focus = 4
                            heat = 0
                            score = 0
                            setScene("New run started.")
                        }) { Text("Restart") }
                    }
                }

                OutlinedButton(onClick = {
                    screen = "Hub"
                    record("Returned to Hub")
                }) { Text("Back to Hub") }
            }
        }

        Text("Build v0.7")
    }
}
