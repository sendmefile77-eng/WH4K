package com.myauko.engine.model

/**
 * Один кадр внутри акта.
 */
data class Frame(
    val id: String,
    val actNumber: Int,
    val frameNumber: Int,
    val baseDescription: String,
    val customDescription: String? = null,
    val focusTags: List<String> = emptyList()
) {
    val effectiveDescription: String
        get() = customDescription ?: baseDescription
}