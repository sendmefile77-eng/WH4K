package com.myauko.engine.model

/**
 * Снимок идентичности визита, чтобы персонаж и стиль не прыгали между кадрами.
 */
data class VisitIdentitySnapshot(
    val moduleId: String,
    val npcId: String,
    val bodyDNA: BodyDNA,
    val createdAtMillis: Long = System.currentTimeMillis(),
    val styleTags: List<String> = emptyList()
)
