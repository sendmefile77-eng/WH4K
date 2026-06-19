package com.myauko.engine.model

/**
 * Живой вектор состояния визита (18 параметров).
 * Эволюционирует во время актов.
 */
data class StateVector(
    val arousal: Float = 0f,
    val flush: Float = 0f,
    val sweat: Float = 0f,
    val toeCurl: Float = 0f,
    val intimateSwelling: Float = 0f,
    val breathing: Float = 0f,
    val eyeContact: Float = 0f,
    val bodyTension: Float = 0f,
    val vocalization: Float = 0f,
    val focusLevel: Float = 0f,
    val emotionalIntensity: Float = 0f,
    val physicalExhaustion: Float = 0f,
    val satisfaction: Float = 0f,
    val dominance: Float = 0f,
    val submission: Float = 0f,
    val tenderness: Float = 0f,
    val urgency: Float = 0f,
    val afterglow: Float = 0f
) {
    fun update(updates: Map<String, Float>): StateVector {
        fun value(name: String, current: Float): Float = updates[name] ?: current

        return copy(
            arousal = value("arousal", arousal),
            flush = value("flush", flush),
            sweat = value("sweat", sweat),
            toeCurl = value("toeCurl", toeCurl),
            intimateSwelling = value("intimateSwelling", intimateSwelling),
            breathing = value("breathing", breathing),
            eyeContact = value("eyeContact", eyeContact),
            bodyTension = value("bodyTension", bodyTension),
            vocalization = value("vocalization", vocalization),
            focusLevel = value("focusLevel", focusLevel),
            emotionalIntensity = value("emotionalIntensity", emotionalIntensity),
            physicalExhaustion = value("physicalExhaustion", physicalExhaustion),
            satisfaction = value("satisfaction", satisfaction),
            dominance = value("dominance", dominance),
            submission = value("submission", submission),
            tenderness = value("tenderness", tenderness),
            urgency = value("urgency", urgency),
            afterglow = value("afterglow", afterglow)
        )
    }
}
