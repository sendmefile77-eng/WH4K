package com.myauko.engine.core.prompt

class PromptAssembler {
    fun assemble(request: PromptRequest): PromptResult {
        val state = request.gameState.stateVector
        val body = request.gameState.bodyDna
        val progress = request.gameState.visitProgress

        val styleSection = request.moduleStyleTags
            .distinct()
            .joinToString(separator = ", ")
            .ifBlank { "cinematic grimdark fantasy" }

        val playerIntent = request.playerCommand
            ?.takeIf { it.isNotBlank() }
            ?.let { sanitizePlayerCommand(it) }
            ?: "continue the current scene beat"

        val frameIntent = request.frameOverride
            ?.takeIf { it.isNotBlank() }
            ?.let { sanitizePlayerCommand(it) }
            ?: "module-defined frame composition"

        val positive = buildString {
            append("fictional adult character only, ")
            append("${body.speciesHint}, ${body.silhouette}, ${body.posture}, ")
            append("stable identity, seed hint ${body.seed}, ")
            append("${progress.act.label}, frame ${progress.frameIndex}, ")
            append("tension ${state.tension}, trust ${state.trust}, danger ${state.danger}, focus ${state.focus}, ")
            append("style: $styleSection, ")
            append("player intent: $playerIntent, ")
            append("frame intent: $frameIntent, ")
            append("cinematic composition, coherent lighting, detailed environment")
        }

        val negative = buildList {
            add("minor")
            add("underage")
            add("teen")
            add("child")
            add("young-looking")
            add("school uniform")
            add("real person likeness")
            add("celebrity likeness")
            add("deepfake")
            add("identity drift")
            add("broken anatomy")
            addAll(request.negativePromptOverrides)
        }.distinct().joinToString(separator = ", ")

        return PromptResult(
            positivePrompt = positive,
            negativePrompt = negative,
            seedHint = body.seed,
            metadata = mapOf(
                "engine" to "WH4K-Core-0.1",
                "moduleId" to request.gameState.activeModuleId,
                "act" to progress.act.name,
                "frameIndex" to progress.frameIndex.toString()
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
