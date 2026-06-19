package com.myauko.engine.prompt

import com.myauko.engine.model.Frame

fun Frame.withCustomization(value: String): Frame = this.copy(customDescription = value)
