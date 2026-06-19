package com.myauko.engine.core.image

import com.myauko.engine.core.prompt.PromptResult
import kotlinx.serialization.Serializable

interface ImageGenerationProvider {
    suspend fun generate(request: ImageGenerationRequest): ImageGenerationResult
}

@Serializable
data class ImageGenerationRequest(
    val prompt: PromptResult,
    val width: Int = 1024,
    val height: Int = 1024,
    val steps: Int = 28,
    val guidanceScale: Float = 7.0f,
    val modelId: String = "nsfw-sdxl",
    val safetyChecker: Boolean = false
)

@Serializable
data class ImageGenerationResult(
    val id: String,
    val imageUrl: String?,
    val localCacheKey: String?,
    val status: ImageGenerationStatus,
    val errorMessage: String? = null
)

@Serializable
enum class ImageGenerationStatus {
    QUEUED,
    RUNNING,
    SUCCEEDED,
    FAILED
}

class FakeImageGenerationProvider : ImageGenerationProvider {
    override suspend fun generate(request: ImageGenerationRequest): ImageGenerationResult {
        return ImageGenerationResult(
            id = "fake-${request.prompt.seedHint}",
            imageUrl = null,
            localCacheKey = "preview/${request.prompt.seedHint}",
            status = ImageGenerationStatus.SUCCEEDED
        )
    }
}
