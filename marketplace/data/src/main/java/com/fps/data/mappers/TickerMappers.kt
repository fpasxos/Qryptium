package com.fps.data.mappers

import com.fps.core.domain.marketplace.CryptoPair
import com.fps.data.models.CryptoPairDto

fun CryptoPairDto.toCryptoPair(): CryptoPair {
    return CryptoPair(
        name = symbol ?: "",
        price = (lastPrice ?: 0.0f).toString(),
        isUptrend = dailyChangePercentage?.times(100)?.compareTo(0.0f) == 1,
        change24h = "%.2f".format(dailyChangePercentage?.times(100) ?: 0.0).toString(),
    )
}