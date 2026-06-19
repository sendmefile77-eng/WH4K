package com.myauko.engine.core.aihorde

import com.myauko.engine.core.image.ImageGenerationProvider
import com.myauko.engine.core.image.ImageGenerationRequest
import com.myauko.engine.core.image.ImageGenerationResult
import com.myauko.engine.core.image.ImageGenerationStatus
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class AiHordeProvider(
    private val apiKey: String = ANONYMOUS_API_KEY,
    private val baseUrl: String = "https://aihorde.net/api/v2",
    private val clientAgent: String = "WH4K:0.1:sendmefile77-eng",
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
) : ImageGenerationProvider {

    override suspend fun generate(request: ImageGenerationRequest): ImageGenerationResult {
        val response = client.post("$baseUrl/generate/async") {
            contentType(ContentType.Application.Json)
            header("apikey", apiKey.ifBlank { ANONYMOUS_API_KEY })
            header("Client-Agent", clientAgent)
            setBody(request.toAiHordeRequest())
        }.body<AiHordeGenerateResponse>()

        val id = response.id
        if (id.isNullOrBlank()) {
            return ImageGenerationResult(
                id = "aihorde-submit-failed",
                imageUrl = null,
                localCacheKey = null,
                status = ImageGenerationStatus.FAILED,
                errorMessage = response.message ?: "AI Horde did not return generation id."
            )
        }

        return ImageGenerationResult(
            id = id,
            imageUrl = null,
            localCacheKey = null,
            status = ImageGenerationStatus.QUEUED,
            errorMessage = response.message
        )
    }

    suspend fun check(id: String): AiHordeCheckResponse {
        return client.get("$baseUrl/generate/check/$id") {
            header("apikey", apiKey.ifBlank { ANONYMOUS_API_KEY })
            header("Client-Agent", clientAgent)
        }.body()
    }

    suspend fun status(id: String): AiHordeStatusResponse {
        return client.get("$baseUrl/generate/status/$id") {
            header("apikey", apiKey.ifBlank { ANONYMOUS_API_KEY })
            header("Client-Agent", clientAgent)
        }.body()
    }

    private fun ImageGenerationRequest.toAiHordeRequest(): AiHordeGenerateRequest {
        val selectedModels = modelId
            ?.takeIf { it.isNotBlank() }
            ?.let { listOf(it) }
            ?: emptyList()

        return AiHordeGenerateRequest(
            prompt = prompt.positivePrompt,
            negativePrompt = prompt.negativePrompt,
            params = AiHordeParams(
                width = width,
                height = height,
                steps = steps,
                cfgScale = guidanceScale,
                n = 1
            ),
            nsfw = allowAdultFictionalContent,
            censorNsfw = safetyChecker,
            trustedWorkers = false,
            models = selectedModels
        )
    }

    private companion object {
        const val ANONYMOUS_API_KEY = "0000000000"
    }
}

@Serializable
data class AiHordeGenerateRequest(
    val prompt: String,
    @SerialName("negative_prompt") val negativePrompt: String,
    val params: AiHordeParams,
    val nsfw: Boolean,
    @SerialName("censor_nsfw") val censorNsfw: Boolean,
    @SerialName("trusted_workers") val trustedWorkers: Boolean = false,
    val models: List<String> = emptyList()
)

@Serializable
data class AiHordeParams(
    val width: Int,
    val height: Int,
    val steps: Int,
    @SerialName("cfg_scale") val cfgScale: Float,
    val n: Int = 1,
    @SerialName("sampler_name") val samplerName: String = "k_euler_a"
)

@Serializable
data class AiHordeGenerateResponse(
    val id: String? = null,
    val kudos: Float? = null,
    val message: String? = null,
    val warnings: List<String> = emptyList()
)

@Serializable
data class AiHordeCheckResponse(
    val done: Boolean = false,
    val processing: Int = 0,
    val waiting: Int = 0,
    @SerialName("wait_time") val waitTime: Int = 0,
    @SerialName("queue_position") val queuePosition: Int? = null,
    val kudos: Float? = null,
    @SerialName("is_possible") val isPossible: Boolean = true
)

@Serializable
data class AiHordeStatusResponse(
    val done: Boolean = false,
    val faulted: Boolean = false,
    val processing: Int = 0,
    val waiting: Int = 0,
    val generations: List<AiHordeGeneration> = emptyList()
)

@Serializable
data class AiHordeGeneration(
    val id: String? = null,
    val img: String? = null,
    val seed: String? = null,
    val censored: Boolean = false,
    val model: String? = null,
    val state: String? = null
)
