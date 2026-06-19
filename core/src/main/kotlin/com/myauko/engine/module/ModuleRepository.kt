package com.myauko.engine.module

import com.myauko.engine.model.Module

/**
 * Репозиторий для работы с модулями (загрузка, сохранение, активация).
 */
interface ModuleRepository {
    suspend fun loadAllModules(): List<Module>
    suspend fun saveModule(module: Module)
    suspend fun getModuleById(id: String): Module?
    suspend fun setActiveModule(id: String)
    suspend fun getActiveModule(): Module?
}