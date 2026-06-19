package com.myauko.engine.core

import com.myauko.engine.model.*

/**
 * Хранит текущее состояние игры.
 */
object GameStateHolder {
    var currentVisit: Visit? = null
        private set

    var activeModule: Module? = null
        private set

    fun setActiveModule(module: Module) {
        activeModule = module
    }

    fun startNewVisit(npcId: String, bodyDNA: BodyDNA) {
        activeModule?.let { module ->
            currentVisit = Visit(
                id = java.util.UUID.randomUUID().toString(),
                moduleId = module.id,
                npcId = npcId,
                bodyDNA = bodyDNA
            )
        }
    }

    fun clearCurrentVisit() {
        currentVisit = null
    }
}