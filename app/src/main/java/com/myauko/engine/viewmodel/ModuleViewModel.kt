package com.myauko.engine.viewmodel

import androidx.lifecycle.ViewModel
import com.myauko.engine.di.AppContainer
import com.myauko.engine.model.Module

class ModuleViewModel : ViewModel() {

    private val moduleLoader = AppContainer.moduleLoader // TODO: заменить на реальный

    fun loadTestModule() {
        // Временная тестовая загрузка модуля
        val testModule = Module(
            id = "wh40k_test",
            name = "Warhammer 40k Test",
            version = "0.1",
            description = "Test module for development",
            styleDescription = "grimdark sci-fi, mature graphic novel style"
        )
        // TODO: Добавить в loader
    }
}