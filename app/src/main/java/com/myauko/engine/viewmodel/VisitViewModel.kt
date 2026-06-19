package com.myauko.engine.viewmodel

import androidx.lifecycle.ViewModel
import com.myauko.engine.core.VisitManager
import com.myauko.engine.model.Visit

class VisitViewModel(
    private val visitManager: VisitManager
) : ViewModel() {

    val currentVisit: Visit?
        get() = visitManager.getCurrentVisit()

    fun advanceFrame(command: String? = null): String? {
        return visitManager.advanceToNextFrame(command)
    }

    fun updateState(updates: Map<String, Float>) {
        visitManager.updateState(updates)
    }
}