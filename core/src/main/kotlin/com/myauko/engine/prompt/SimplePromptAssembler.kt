package com.myauko.engine.prompt

import com.myauko.engine.model.*

class SimplePromptAssembler : PromptAssembler {

    override fun assemble(request: PromptBuildRequest): PromptBuildResult {
        val visit = request.visit
        val frame = request.frame
        val command = request.playerCommand?.lowercase() ?: ""
        val module = request.module

        val style = module.styleDescription.ifBlank { "grimdark WH40K erotic, sensual adult comic realism" }

        // Base erotic grimdark tone
        val base = buildString {
            append(style)
            append(", highly detailed anatomy, dramatic lighting, gothic sci-fi atmosphere, mature erotic scene")
        }

        // Scene context based on act
        val sceneContext = when (visit.currentAct) {
            1 -> "private cabin meeting, initial tension and anticipation, elegant yet revealing clothing"
            2 -> "escalating intimacy, clothing partially removed, strong eye contact and physical tension"
            3 -> "intense erotic moment, bodies closer, raw desire, detailed physical interaction"
            else -> "deeply intimate and explicit scene, full focus on bodies and sensation"
        }

        // Handle foot focus specially (very important for this project)
        val focus = if (command.contains("foot") || command.contains("feet") || command.contains("фут")) {
            "strong emphasis on feet, high arches, detailed soles and toes, foot worship elements, elegant legs, body worship"
        } else if (command.contains("more")) {
            "increased intensity and explicitness, more sensual details"
        } else {
            "sensual full body focus, elegant pose, detailed skin and curves"
        }

        // Character placeholder (will be replaced with real data later)
        val character = "beautiful mature woman with athletic build, long hair, intense eyes, wearing partial commissar-inspired attire"

        val positive = listOf(
            base,
            sceneContext,
            focus,
            character,
            "act ${visit.currentAct}, frame ${visit.currentFrame}",
            "consistent character identity",
            "highly detailed, sharp focus, cinematic composition"
        ).joinToString(", ")

        val negative = "low quality, blurry, deformed, extra limbs, bad anatomy, child, underage, text, watermark"

        return PromptBuildResult(
            positivePrompt = positive,
            negativePrompt = negative,
            seedHint = "${visit.id}:${frame.id}:${visit.currentAct}:${visit.currentFrame}"
        )
    }

    // Legacy method used by VisitManager
    fun assembleForFrame(
        frame: Frame,
        visit: Visit,
        activeModule: Module,
        playerCommand: String? = null
    ): String {
        val request = PromptBuildRequest(
            visit = visit,
            frame = frame,
            module = activeModule,
            playerCommand = playerCommand
        )
        return assemble(request).positivePrompt
    }
}