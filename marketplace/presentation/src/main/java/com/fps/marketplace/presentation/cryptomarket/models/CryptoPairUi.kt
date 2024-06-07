package com.fps.marketplace.presentation.cryptomarket.models

data class CryptoPairUi(
    val name: String,
    val price: String,
    val isUptrend: Boolean,
    val change24h: String
) {
    val image: String
        get() = "https://static.bitfinex.com/images/icons/${(name)}.svg"
}