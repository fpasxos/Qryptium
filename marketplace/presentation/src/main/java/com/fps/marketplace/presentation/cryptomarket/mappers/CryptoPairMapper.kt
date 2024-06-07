package com.fps.marketplace.presentation.cryptomarket.mappers

import com.fps.core.domain.marketplace.CryptoPair
import com.fps.marketplace.presentation.cryptomarket.models.CryptoPairUi

fun CryptoPair.toCryptoPairUi(): CryptoPairUi {
    return CryptoPairUi(
        name = extractCryptoSymbol(name),
        price = "$ $price",
        isUptrend = isUptrend,
        change24h = "$change24h%"
    )
}

private fun extractCryptoSymbol(pair: String): String {
    val regex = Regex("^t([A-Z]+):?USD$")
    val matchResult = regex.find(pair)
    return matchResult?.groups?.get(1)?.value ?: "Unknown"
}