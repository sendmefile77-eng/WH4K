package com.myauko.engine.module

import com.myauko.engine.model.Module

/**
 * Интерфейс для загрузки и управления модулями.
 */
interface ModuleLoader {
    fun loadModule(moduleId: String): Module?
    fun getActiveModule(): Module?
    fun setActiveModule(moduleId: String): Boolean
    fun listAvailableModules(): List<Module>
}