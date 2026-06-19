package com.wh4k.myauko.core.atlas

import com.wh4k.myauko.core.prompt.PromptResult
import kotlinx.serialization.Serializable

interface AtlasCloudClient {
    suspend fun generateImage(request: AtlasImageRequest): AtlasImageResult
}

@Serializable
data class AtlasImageRequest(
    val prompt: PromptResult,
    val width: Int = 1024,
    val height: Int = 1024,
    val steps: Int = 28,
    val guidanceScale: Float = 7.0f,
    val model: String = "atlas-default"
)

@Serializable
data class AtlasImageResult(
    val id: String,
    val imageUrl: String?,
    val localCacheKey: String?,
    val status: AtlasJobStatus,
    val errorMessage: String? = null
)

@Serializable
enum class AtlasJobStatus {
    QUEUED,
    RUNNING,
    SUCCEEDED,
    FAILED
}

class FakeAtlasCloudClient : AtlasCloudClient {
    override suspend fun generateImage(request: AtlasImageRequest): AtlasImageResult {
        return AtlasImageResult(
            id = "local-preview-${request.prompt.seedHint}",
            imageUrl = null,
            localCacheKey = "preview/${request.prompt.seedHint}",
            status = AtlasJobStatus.SUCCEEDED
        )
    }
}
