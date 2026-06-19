package com.wh4k.myauko.core.state

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val stateVector: StateVector,
    val bodyDna: BodyDna,
    val visitProgress: VisitProgress,
    val identitySnapshot: VisitIdentitySnapshot?
) {
    companion object {
        fun newGame(): GameState = GameState(
            stateVector = StateVector.neutral(),
            bodyDna = BodyDna.seeded("default-npc-seed"),
            visitProgress = VisitProgress.initial(),
            identitySnapshot = null
        )
    }
}

@Serializable
data class StateVector(
    val tension: Float,
    val trust: Float,
    val curiosity: Float,
    val fatigue: Float,
    val danger: Float,
    val agency: Float,
    val distance: Float,
    val closeness: Float,
    val mystery: Float,
    val control: Float,
    val chaos: Float,
    val focus: Float,
    val momentum: Float,
    val ambience: Float,
    val resistance: Float,
    val vulnerability: Float,
    val dominancePressure: Float,
    val consentGate: Float
) {
    companion object {
        fun neutral(): StateVector = StateVector(
            tension = 0.25f,
            trust = 0.5f,
            curiosity = 0.5f,
            fatigue = 0.0f,
            danger = 0.15f,
            agency = 1.0f,
            distance = 0.5f,
            closeness = 0.2f,
            mystery = 0.5f,
            control = 0.5f,
            chaos = 0.1f,
            focus = 0.5f,
            momentum = 0.0f,
            ambience = 0.5f,
            resistance = 0.0f,
            vulnerability = 0.0f,
            dominancePressure = 0.0f,
            consentGate = 1.0f
        )
    }
}

@Serializable
data class BodyDna(
    val seed: String,
    val speciesHint: String,
    val silhouette: String,
    val heightClass: String,
    val buildClass: String,
    val posture: String,
    val hairProfile: String,
    val eyeProfile: String,
    val distinguishingMarks: List<String>
) {
    companion object {
        fun seeded(seed: String): BodyDna = BodyDna(
            seed = seed,
            speciesHint = "humanoid-adult",
            silhouette = "distinctive modular character silhouette",
            heightClass = "medium",
            buildClass = "athletic",
            posture = "confident",
            hairProfile = "module-defined",
            eyeProfile = "module-defined",
            distinguishingMarks = emptyList()
        )
    }
}

@Serializable
data class VisitIdentitySnapshot(
    val moduleId: String,
    val directionId: String,
    val npcSeed: String,
    val bodyDna: BodyDna,
    val startedAtEpochMillis: Long,
    val selectedStyleTags: List<String>
)

@Serializable
data class VisitProgress(
    val act: VisitAct,
    val frameIndex: Int,
    val isPausedForPlayerInput: Boolean
) {
    companion object {
        fun initial(): VisitProgress = VisitProgress(
            act = VisitAct.ACT_I,
            frameIndex = 0,
            isPausedForPlayerInput = false
        )
    }
}

@Serializable
enum class VisitAct(val label: String) {
    ACT_I("Act I"),
    ACT_II("Act II"),
    ACT_III("Act III"),
    ACT_IV("Act IV"),
    ACT_V("Act V")
}
