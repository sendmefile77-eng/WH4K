package com.wh4k.myauko.core.module

import kotlinx.serialization.Serializable

@Serializable
data class ModuleManifest(
    val id: String,
    val name: String,
    val version: String,
    val minEngineVersion: String,
    val contentRating: ContentRating = ContentRating.ADULT_ONLY,
    val directions: List<ModuleDirection>,
    val banks: ModuleBanks,
    val promptPacks: List<String>,
    val styleTags: List<String>,
    val safety: ModuleSafety = ModuleSafety()
)

@Serializable
enum class ContentRating {
    GENERAL,
    TEEN,
    ADULT_ONLY
}

@Serializable
data class ModuleDirection(
    val id: String,
    val title: String,
    val description: String,
    val showcasePromptKey: String,
    val visitScriptKey: String
)

@Serializable
data class ModuleBanks(
    val characters: String,
    val locations: String,
    val atmospheres: String,
    val frameTemplates: String
)

@Serializable
data class ModuleSafety(
    val requiresAdultCharacters: Boolean = true,
    val disallowMinors: Boolean = true,
    val disallowRealPersonLikeness: Boolean = true,
    val disallowIllegalContent: Boolean = true
)
