package com.myauko.engine.core.module

import kotlinx.serialization.json.Json

class ModuleLoader(
    private val json: Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
) {
    fun parseManifest(rawJson: String): ModuleManifest {
        val manifest = json.decodeFromString<ModuleManifest>(rawJson)
        require(manifest.id.isNotBlank()) { "Module id must not be blank." }
        require(manifest.directions.isNotEmpty()) { "Module must define at least one direction." }
        require(manifest.safety.requiresAdultCharacters) { "Module must require adult fictional characters." }
        require(manifest.safety.disallowMinors) { "Module must disallow minor characters." }
        require(manifest.safety.disallowRealPersonLikeness) { "Module must disallow real-person likeness by default." }
        return manifest
    }
}
