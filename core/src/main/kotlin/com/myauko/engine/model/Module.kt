package com.myauko.engine.model

/**
 * Модуль (сеттинг).
 */
data class Module(
    val id: String,
    val name: String,
    val version: String,
    val description: String,
    val styleDescription: String,
    val banks: Map<String, Any> = emptyMap(),
    val menus: Map<String, Any> = emptyMap(),
    val promptTemplates: Map<String, String> = emptyMap(),
    val isActive: Boolean = false
)