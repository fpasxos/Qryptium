package com.fps.marketplace.presentation.cryptomarket

import com.fps.core.presentation.ui.UiText
import com.fps.marketplace.presentation.cryptomarket.models.CryptoPairUi

data class CryptoMarketState(
    val isLoading: Boolean = false,
    val isConnectedToNetwork: Boolean = false,
    val error: UiText? = null,
    val searchQuery: String = "",
    val cryptoPairsList: List<CryptoPairUi> = emptyList(),
    val cryptoPairsFilteredList: List<CryptoPairUi> = emptyList()
)