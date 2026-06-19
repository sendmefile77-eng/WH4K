package com.myauko.engine.image

/**
 * Интерфейс для генерации изображений.
 * Позже подключим реальный API (Atlas Cloud и ли другой).
 */
interface ImageGenerationService {
    suspend fun generateImage(prompt: String): String? // возвращает URL или base64
}