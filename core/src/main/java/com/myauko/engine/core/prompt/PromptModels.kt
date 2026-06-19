package com.myauko.engine.core.prompt

import com.myauko.engine.core.state.GameState
import kotlinx.serialization.Serializable

@Serializable
data class PromptRequest(
    val gameState: GameState,
    val playerCommand: String?,
    val moduleStyleTags: List<String>,
    val frameOverride: String? = null,
    val negativePromptOverrides: List<String> = emptyList()
)

@Serializable
data class PromptResult(
    val positivePrompt: String,
    val negativePrompt: String,
    val seedHint: String,
    val metadata: Map<String, String>
)
