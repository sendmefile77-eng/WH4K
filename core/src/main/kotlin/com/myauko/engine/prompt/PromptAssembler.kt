package com.myauko.engine.prompt

import com.myauko.engine.model.*

/**
 * Умный сборщик промптов.
 * Берёт данные модуля + текущее состояние + команды игрока
 * и собирает финальный промпт для API генерации изображений.
 */
interface PromptAssembler {

    fun assembleForFrame(
        frame: Frame,
        visit: Visit,
        activeModule: Module,
        playerCommand: String? = null
    ): String

    fun rebuildPrompt(
        originalPrompt: String,
        newDescription: String,
        visit: Visit,
        activeModule: Module
    ): String
}