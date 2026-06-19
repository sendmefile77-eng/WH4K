package com.myauko.engine.core

import com.myauko.engine.model.*
import com.myauko.engine.prompt.PromptAssembler

/**
 * Управляет жизненным циклом визита.
 */
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

    fun advanceFrame(playerCommand: String? = null): String? {
        val visit = currentVisit ?: return null

        // TODO: Логика перехода по кадрам и актам

        val frame = Frame(
            id = "frame_${visit.currentAct}_${visit.currentFrame}",
            actNumber = visit.currentAct,
            frameNumber = visit.currentFrame,
            baseDescription = "Базовое описание кадра"
        )

        val prompt = promptAssembler.assembleForFrame(
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

        // TODO: Отправить prompt в API и получить изображение

        return prompt // временно возвращаем prompt
    }

    fun updateState(updates: Map<String, Float>) {
        currentVisit = currentVisit?.copy(
            stateVector = currentVisit?.stateVector?.update(updates) ?: StateVector()
        )
    }
}