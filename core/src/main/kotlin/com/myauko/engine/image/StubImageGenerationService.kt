package com.myauko.engine.image

/**
 * Заглушка ImageGenerationService.
 * Вернет placeholder вместо реального изображения.
 * Позже заменим на реальный вызов Atlas Cloud.
 */
class StubImageGenerationService : ImageGenerationService {
    override suspend fun generateImage(prompt: String): String? {
        // В реальной реализации здесь будет вызов к API
        return "https://picsum.photos/512/512" // placeholder
    }
}