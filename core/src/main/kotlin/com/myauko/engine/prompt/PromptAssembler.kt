package com.myauko.engine.prompt

import com.myauko.engine.model.Frame
import com.myauko.engine.model.Module
import com.myauko.engine.model.Visit

interface PromptAssembler {
    fun assemble(request: PromptBuildRequest): PromptBuildResult

    fun assembleForFrame(
        frame: Frame,
        visit: Visit,
        activeModule: Module,
        playerCommand: String? = null
    ): String = assemble(
        PromptBuildRequest(
            frame = frame,
            visit = visit,
            module = activeModule,
            playerCommand = playerCommand
        )
    ).positivePrompt

    fun rebuildPrompt(
        originalPrompt: String,
        newDescription: String,
        visit: Visit,
        activeModule: Module
    ): String = assemble(
        PromptBuildRequest(
            frame = visit.currentFrameModel().withCustomization(newDescription),
            visit = visit,
            module = activeModule,
            playerCommand = newDescription,
            previousPrompt = originalPrompt
        )
    ).positivePrompt
}

data class PromptBuildRequest(
    val frame: Frame,
    val visit: Visit,
    val module: Module,
    val playerCommand: String? = null,
    val previousPrompt: String? = null
)

data class PromptBuildResult(
    val positivePrompt: String,
    val negativePrompt: String,
    val seedHint: String,
    val metadata: Map<String, String> = emptyMap()
)
