package com.myauko.engine.prompt

import com.myauko.engine.model.Frame
import com.myauko.engine.model.Visit

fun Visit.currentFrameModel(): Frame = Frame(
    id = "${id}_${currentAct}_${currentFrame}",
    actNumber = currentAct,
    frameNumber = currentFrame,
    baseDescription = "Runtime frame"
)
