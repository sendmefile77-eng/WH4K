package com.myauko.engine.model

fun Visit.actLabel(): String = when (currentAct) {
    1 -> "Act I"
    2 -> "Act II"
    3 -> "Act III"
    4 -> "Act IV"
    5 -> "Act V"
    else -> "Act"
}

fun Visit.normalizedTier(): Int = tier.coerceIn(0, 21)

fun Visit.currentFrameModel(): Frame = Frame(
    id = "${id}_${currentAct}_${currentFrame}",
    actNumber = currentAct,
    frameNumber = currentFrame,
    baseDescription = "Runtime frame"
)

fun Visit.nextFrameOrPause(framesPerAct: Int = 3): Visit {
    if (isCompleted) return this
    val next = currentFrame + 1
    return when {
        next < framesPerAct -> copy(currentFrame = next)
        currentAct < 5 -> copy(currentFrame = 0, currentAct = currentAct + 1)
        else -> copy(isCompleted = true)
    }
}

fun Visit.withFrameDelta(delta: Map<String, Float>): Visit = copy(
    stateVector = stateVector.update(delta)
)
