package com.myauko.engine.viewmodel

import androidx.lifecycle.ViewModel
import com.myauko.engine.core.GameStateHolder
import com.myauko.engine.model.Module

class HubViewModel : ViewModel() {

    val activeModule: Module?
        get() = GameStateHolder.activeModule

    fun loadModule(module: Module) {
        GameStateHolder.setActiveModule(module)
    }
}