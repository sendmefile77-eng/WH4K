package com.wh4k.myauko.core.prompt

class PromptAssembler {
    fun assemble(request: PromptRequest): PromptResult {
        val state = request.gameState.stateVector
        val body = request.gameState.bodyDna
        val progress = request.gameState.visitProgress

        val styleSection = request.moduleStyleTags
            .distinct()
            .joinToString(separator = ", ")

        val playerIntent = request.playerCommand
            ?.takeIf { it.isNotBlank() }
            ?.let { sanitizePlayerCommand(it) }
            ?: "continue the current scene beat"

        val frameIntent = request.frameOverride
            ?.takeIf { it.isNotBlank() }
            ?.let { sanitizePlayerCommand(it) }
            ?: "module-defined frame composition"

        val positive = buildString {
            append("adult-only fictional character, ")
            append("${body.speciesHint}, ${body.silhouette}, ${body.posture} posture, ")
            append("act=${progress.act.label}, frame=${progress.frameIndex}, ")
            append("mood tension=${state.tension}, trust=${state.trust}, danger=${state.danger}, ")
            append("style: $styleSection, ")
            append("player intent: $playerIntent, ")
            append("frame intent: $frameIntent, ")
            append("cinematic composition, consistent identity, coherent lighting")
        }

        val negative = buildList {
            add("minor")
            add("underage")
            add("real person likeness")
            add("illegal content")
            add("identity drift")
            add("broken anatomy")
            addAll(request.negativePromptOverrides)
        }.distinct().joinToString(separator = ", ")

        return PromptResult(
            positivePrompt = positive,
            negativePrompt = negative,
            seedHint = body.seed,
            metadata = mapOf(
                "act" to progress.act.name,
                "frameIndex" to progress.frameIndex.toString(),
                "engine" to "WH4K-Core-0.1"
            )
        )
    }

    private fun sanitizePlayerCommand(raw: String): String = raw
        .trim()
        .replace(Regex("\\s+"), " ")
        .take(MAX_COMMAND_CHARS)

    private companion object {
        const val MAX_COMMAND_CHARS = 300
    }
}
