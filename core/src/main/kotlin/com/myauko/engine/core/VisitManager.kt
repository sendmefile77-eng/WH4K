package com.myauko.engine.core

import com.myauko.engine.model.*
import com.myauko.engine.prompt.PromptAssembler

class VisitManager(
    private val promptAssembler: PromptAssembler
) {
    private var currentVisit: Visit? = null

    fun startVisit(module: Module, npcId: String, bodyDNA: BodyDNA): Visit {
        val visit = Visit(
            id = java.util.UUID.randomUUID().toString(),
            moduleId = module.id,
            npcId = npcId,
            bodyDNA = bodyDNA
        )
        currentVisit = visit
        return visit
    }

    fun getCurrentVisit(): Visit? = currentVisit

    fun advanceToNextFrame(playerCommand: String? = null): String? = advanceFrame(playerCommand)

    fun advanceFrame(playerCommand: String? = null): String? {
        val visit = currentVisit ?: return null
        val frame = Frame(
            id = "frame_${visit.currentAct}_${visit.currentFrame}",
            actNumber = visit.currentAct,
            frameNumber = visit.currentFrame,
            baseDescription = "Frame"
        )
        return promptAssembler.assembleForFrame(
            frame = frame,
            visit = visit,
            activeModule = Module(
                id = visit.moduleId,
                name = "",
                version = "",
                description = "",
                styleDescription = ""
            ),
            playerCommand = playerCommand
        )
    }

    fun updateState(updates: Map<String, Float>) {
        currentVisit = currentVisit?.copy(
            stateVector = currentVisit?.stateVector?.update(updates) ?: StateVector()
        )
    }
}
