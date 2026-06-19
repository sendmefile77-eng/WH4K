package com.myauko.engine.prompt

class HordePromptAssembler : PromptAssembler {
    private val delegate = SimplePromptAssembler()

    override fun assemble(request: PromptBuildRequest): PromptBuildResult {
        val base = delegate.assemble(request)
        val hordePrompt = listOf(
            base.positivePrompt,
            "high detail",
            "coherent lighting",
            "single consistent character",
            "module=${request.module.id}"
        ).joinToString(", ")

        return base.copy(
            positivePrompt = hordePrompt,
            metadata = base.metadata + mapOf(
                "provider" to "ai_horde",
                "module" to request.module.id
            )
        )
    }
}
