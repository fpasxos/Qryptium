package com.fps.marketplace.presentation.cryptomarket.fakes

import com.fps.core.domain.connectivity.NetworkConnectivityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeConnectivityManager : com.fps.core.domain.connectivity.ConnectivityManager {

    private val _networkState =
        MutableStateFlow<NetworkConnectivityState>(NetworkConnectivityState.Available)
    val networkState: StateFlow<NetworkConnectivityState> get() = _networkState

    override fun networkState(): kotlinx.coroutines.flow.Flow<NetworkConnectivityState> =
        networkState

    fun setNetworkState(state: NetworkConnectivityState) {
        _networkState.value = state
    }
}