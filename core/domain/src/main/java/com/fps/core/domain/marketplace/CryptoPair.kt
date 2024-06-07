package com.fps.core.domain.marketplace

data class CryptoPair(
    val name: String,
    val price: String,
    val change24h: String,
    val isUptrend: Boolean
)
