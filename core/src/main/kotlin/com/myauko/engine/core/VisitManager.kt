package com.myauko.engine.core

import com.myauko.engine.model.*
import com.myauko.engine.prompt.PromptAssembler

class VisitManager(
    private val promptAssembler: PromptAssembler
) {
    private var currentVisit: Visit? = null
    private val maxFramesPerAct = 3

    fun startVisit(module: Module, npcId: String, bodyDNA: BodyDNA): Visit {
        val visit = Visit(
            id = java.util.UUID.randomUUID().toString(),
            moduleId = module.id,
            npcId = npcId,
            bodyDNA = bodyDNA,
            currentAct = 1,
            currentFrame = 1
        )
        currentVisit = visit
        return visit
    }

    fun getCurrentVisit(): Visit? = currentVisit

    fun advanceToNextFrame(playerCommand: String? = null): String {
        val visit = currentVisit ?: return "No active visit"

        var nextFrame = visit.currentFrame + 1
        var nextAct = visit.currentAct

        if (nextFrame > maxFramesPerAct) {
            nextFrame = 1
            nextAct = (visit.currentAct + 1).coerceAtMost(5) // Max 5 acts for demo
        }

        // Create richer frame context
        val actIntensity = when (nextAct) {
            1 -> "initial tension and anticipation"
            2 -> "building desire and intimacy"
            3 -> "intense physical connection"
            4 -> "deeply explicit and raw"
            else -> "climax and total surrender"
        }

        val frame = Frame(
            id = "frame_${nextAct}_$nextFrame",
            actNumber = nextAct,
            frameNumber = nextFrame,
            baseDescription = "Act $nextAct - Frame $nextFrame ($actIntensity)"
        )

        // Better module context for the assembler
        val activeModule = Module(
            id = visit.moduleId,
            name = "wh4k_myauko",
            version = "0.2",
            description = "Mature grimdark erotic cabin visit",
            styleDescription = "grimdark WH40K erotic, sensual adult comic realism, highly detailed anatomy, gothic atmosphere"
        )

        val prompt = promptAssembler.assembleForFrame(
            frame = frame,
            visit = visit.copy(currentAct = nextAct, currentFrame = nextFrame),
            activeModule = activeModule,
            playerCommand = playerCommand
        )

        currentVisit = visit.copy(
            currentAct = nextAct,
            currentFrame = nextFrame
        )

        return prompt
    }

    fun updateState(updates: Map<String, Float>) {
        currentVisit = currentVisit?.copy(
            stateVector = currentVisit?.stateVector?.update(updates) ?: StateVector()
        )
    }

    fun getCurrentActAndFrame(): Pair<Int, Int> {
        val v = currentVisit ?: return 1 to 1
        return v.currentAct to v.currentFrame
    }
}