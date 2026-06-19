package com.myauko.engine.model

import java.util.UUID

/**
 * Уникальная "ДНК тела" NPC. Генерируется один раз при создании персонажа
 * и остаётся неизменной навсегда (для consistency).
 */
data class BodyDNA(
    val id: String = UUID.randomUUID().toString(),
    val height: Float,
    val build: Float,
    val breastSize: Float,
    val hipWidth: Float,
    val waist: Float,
    val legLength: Float,
    val footSize: Float,
    val skinTone: String,
    val hairColor: String,
    val eyeColor: String,
    val distinctiveFeatures: List<String> = emptyList()
)