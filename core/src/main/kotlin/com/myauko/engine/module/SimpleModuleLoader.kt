package com.myauko.engine.module

import com.myauko.engine.model.Module

/**
 * Простая реализация ModuleLoader (in-memory).
 * Позже заменить на загрузку из файлов / GitHub.
 */
class SimpleModuleLoader : ModuleLoader {

    private val modules = mutableMapOf<String, Module>()
    private var activeModuleId: String? = null

    override fun loadModule(moduleId: String): Module? {
        return modules[moduleId]
    }

    override fun getActiveModule(): Module? {
        return activeModuleId?.let { modules[it] }
    }

    override fun setActiveModule(moduleId: String): Boolean {
        return if (modules.containsKey(moduleId)) {
            activeModuleId = moduleId
            true
        } else {
            false
        }
    }

    override fun listAvailableModules(): List<Module> {
        return modules.values.toList()
    }

    // Для тестов
    fun addModule(module: Module) {
        modules[module.id] = module
    }
}