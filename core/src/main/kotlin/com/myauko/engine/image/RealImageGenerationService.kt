package com.myauko.engine.image

/**
 * Реальная реализация ImageGenerationService.
 * Здесь будет вызов к Atlas Cloud API.
 */
class RealImageGenerationService : ImageGenerationService {

    override suspend fun generateImage(prompt: String): String? {
        // TODO: Здесь будет реальный вызов к API Atlas Cloud
        // Пока возвращаем placeholder
        return "https://picsum.photos/seed/${prompt.hashCode()}/512/512"
    }
}