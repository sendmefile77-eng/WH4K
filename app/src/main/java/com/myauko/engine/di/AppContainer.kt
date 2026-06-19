package com.myauko.engine.di

import com.myauko.engine.core.VisitManager
import com.myauko.engine.image.StubImageGenerationService
import com.myauko.engine.prompt.SimplePromptAssembler

/**
 * Простой DI контейнер (пока вручную).
 * Позже можно заменить на Hilt / Koin.
 */
object AppContainer {

    val promptAssembler by lazy { SimplePromptAssembler() }
    val imageService by lazy { StubImageGenerationService() }
    val visitManager by lazy { VisitManager(promptAssembler) }

    // TODO: Добавить ModuleRepository и другие зависимости
}