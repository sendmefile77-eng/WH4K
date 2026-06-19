package com.myauko.engine.prompt

class SimplePromptAssembler : PromptAssembler {
    override fun assemble(request: PromptBuildRequest): PromptBuildResult {
        val text = listOf(
            request.module.styleDescription,
            request.frame.effectiveDescription,
            "act=${request.visit.currentAct}",
            "frame=${request.visit.currentFrame}",
            "k=${request.visit.tier.coerceIn(0, 21)}",
            request.playerCommand.orEmpty(),
            "consistent identity",
            "stable composition"
        ).filter { it.isNotBlank() }.joinToString(", ")

        return PromptBuildResult(
            positivePrompt = text,
            negativePrompt = "low_quality",
            seedHint = "${request.visit.id}:${request.frame.id}"
        )
    }
}
