package com.wh4k.myauko.core.module

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
        require(manifest.safety.disallowMinors) { "Modules must explicitly disallow minor characters." }
        return manifest
    }
}
