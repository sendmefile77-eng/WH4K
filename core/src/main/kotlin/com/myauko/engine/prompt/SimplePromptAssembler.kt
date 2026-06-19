package com.myauko.engine.prompt

import com.myauko.engine.model.*

/**
 * Простая реализация PromptAssembler.
 * Делает умную перестройку промпта.
 */
class SimplePromptAssembler : PromptAssembler {

    override fun assembleForFrame(
        frame: Frame,
        visit: Visit,
        activeModule: Module,
        playerCommand: String?
    ): String {
        val base = frame.effectiveDescription
        val style = activeModule.styleDescription

        val stateInfo = buildString {
            if (visit.stateVector.arousal > 0.6f) append("\u0432ысокое возбуждение, ")
            if (visit.stateVector.flush > 0.5f) append("\u0441ильный румянец, ")
            if (visit.stateVector.toeCurl > 0.4f) append("\u043fальцы ног сжаты, ")
        }

        val commandPart = playerCommand?.let { "\u0414ополнительно: $it. " } ?: ""

        return buildString {
            append("$style. ")
            append("$base. ")
            if (stateInfo.isNotBlank()) append("\u0421остояние: $stateInfo. ")
            append(commandPart)
            append("\u0412ысокое качество, детализация, consistent с предыдущими кадрами.")
        }
    }

    override fun rebuildPrompt(
        originalPrompt: String,
        newDescription: String,
        visit: Visit,
        activeModule: Module
    ): String {
        val style = activeModule.styleDescription
        return "$style. $newDescription. \u0421остояние: \u0432ысокое возбуждение. \u0414етализация, consistent персонаж."
    }
}