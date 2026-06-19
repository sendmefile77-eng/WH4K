package com.myauko.engine.core.modelslab

import com.myauko.engine.core.image.ImageGenerationProvider
import com.myauko.engine.core.image.ImageGenerationRequest
import com.myauko.engine.core.image.ImageGenerationResult
import com.myauko.engine.core.image.ImageGenerationStatus
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ModelsLabProvider(
    private val apiKey: String,
    private val baseUrl: String = "https://modelslab.com/api/v6",
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
) : ImageGenerationProvider {
    override suspend fun generate(request: ImageGenerationRequest): ImageGenerationResult {
        if (apiKey.isBlank()) {
            return ImageGenerationResult(
                id = "modelslab-not-configured",
                imageUrl = null,
                localCacheKey = null,
                status = ImageGenerationStatus.FAILED,
                errorMessage = "ModelsLab API key is not configured."
            )
        }

        val response = client.post("$baseUrl/images/text2img") {
            contentType(ContentType.Application.Json)
            setBody(request.toModelsLabRequest(apiKey))
        }.body<ModelsLabTextToImageResponse>()

        val firstOutput = response.output.firstOrNull()
        return ImageGenerationResult(
            id = response.id ?: "modelslab-${request.prompt.seedHint}",
            imageUrl = firstOutput,
            localCacheKey = null,
            status = if (response.status.equals("success", ignoreCase = true)) {
                ImageGenerationStatus.SUCCEEDED
            } else {
                ImageGenerationStatus.QUEUED
            },
            errorMessage = response.message
        )
    }
}

private fun ImageGenerationRequest.toModelsLabRequest(apiKey: String): ModelsLabTextToImageRequest {
    return ModelsLabTextToImageRequest(
        key = apiKey,
        prompt = prompt.positivePrompt,
        negativePrompt = prompt.negativePrompt,
        width = width.toString(),
        height = height.toString(),
        samples = "1",
        numInferenceSteps = steps.toString(),
        guidanceScale = guidanceScale,
        modelId = modelId,
        safetyChecker = if (safetyChecker) "yes" else "no",
        seed = prompt.seedHint.hashCode().toString()
    )
}

@Serializable
data class ModelsLabTextToImageRequest(
    val key: String,
    val prompt: String,
    @SerialName("negative_prompt") val negativePrompt: String,
    val width: String,
    val height: String,
    val samples: String,
    @SerialName("num_inference_steps") val numInferenceSteps: String,
    @SerialName("guidance_scale") val guidanceScale: Float,
    @SerialName("model_id") val modelId: String,
    @SerialName("safety_checker") val safetyChecker: String,
    val seed: String
)

@Serializable
data class ModelsLabTextToImageResponse(
    val status: String? = null,
    val id: String? = null,
    val output: List<String> = emptyList(),
    val message: String? = null
)
