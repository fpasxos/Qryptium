package com.fps.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CryptoPairDto(
    val symbol: String? = "",
    val bid: Double? = 0.0,
    val bidSize: Double? = 0.0,
    val ask: Double? = 0.0,
    val askSize: Double? = 0.0,
    val dailyChange: Double? = 0.0,
    val dailyChangePercentage: Double? = 0.0,
    val lastPrice: Float? = 0.0f,
    val volume: Double? = 0.0,
    val high: Double? = 0.0,
    val low: Double? = 0.0
)