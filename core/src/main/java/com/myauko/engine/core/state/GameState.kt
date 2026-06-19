package com.myauko.engine.core.state

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val stateVector: StateVector,
    val bodyDna: BodyDna,
    val visitProgress: VisitProgress,
    val activeModuleId: String = "example.grimdark"
) {
    companion object {
        fun newGame(): GameState = GameState(
            stateVector = StateVector.neutral(),
            bodyDna = BodyDna.seeded("local-preview-seed"),
            visitProgress = VisitProgress.initial()
        )
    }
}

@Serializable
data class StateVector(
    val tension: Float,
    val trust: Float,
    val curiosity: Float,
    val danger: Float,
    val agency: Float,
    val focus: Float,
    val momentum: Float,
    val ambience: Float,
    val consentGate: Float
) {
    companion object {
        fun neutral(): StateVector = StateVector(
            tension = 0.25f,
            trust = 0.5f,
            curiosity = 0.5f,
            danger = 0.15f,
            agency = 1.0f,
            focus = 0.5f,
            momentum = 0.0f,
            ambience = 0.5f,
            consentGate = 1.0f
        )
    }
}

@Serializable
data class BodyDna(
    val seed: String,
    val speciesHint: String,
    val silhouette: String,
    val posture: String,
    val hairProfile: String,
    val eyeProfile: String
) {
    companion object {
        fun seeded(seed: String): BodyDna = BodyDna(
            seed = seed,
            speciesHint = "fictional adult humanoid",
            silhouette = "module-defined distinctive silhouette",
            posture = "confident cinematic posture",
            hairProfile = "module-defined",
            eyeProfile = "module-defined"
        )
    }
}

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
