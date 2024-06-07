package com.fps.marketplace.presentation.cryptomarket

sealed interface CryptoMarketAction {
    data object OnRefreshAfterError : CryptoMarketAction
    data object OnPullToRefresh : CryptoMarketAction
    data object OnClearSearch : CryptoMarketAction
    data class OnSearch(val query: String) : CryptoMarketAction
}