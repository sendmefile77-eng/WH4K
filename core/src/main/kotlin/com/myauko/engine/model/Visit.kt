package com.myauko.engine.model

/**
 * Один визит (посещение).
 */
data class Visit(
    val id: String,
    val moduleId: String,
    val npcId: String,
    val bodyDNA: BodyDNA,
    val stateVector: StateVector = StateVector(),
    val currentAct: Int = 1,
    val currentFrame: Int = 0,
    val tier: Int = 0,
    val identitySnapshot: VisitIdentitySnapshot? = null,
    val generatedImages: MutableMap<String, String> = mutableMapOf(),
    val isCompleted: Boolean = false
)